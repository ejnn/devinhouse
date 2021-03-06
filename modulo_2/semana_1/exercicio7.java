import java.lang.Math;

class ContaBancaria {

    public static final double SAQUE_MAXIMO = 2000;
    private static final UniqueIdGenerator idGenerator = new UniqueIdGenerator();

    private int identificador = ContaBancaria.idGenerator.generateId();
    private String nomeDoTitular;
    private int numero;
    private int agencia;
    private double saldo;
    private String dataDeAbertura;


    public ContaBancaria() {
    }

    public ContaBancaria(double saldo) {
	this.saldo = saldo;
    }


    public void sacar(double valor) {

	double novoSaldo = this.saldo - Math.min(SAQUE_MAXIMO, valor);

	if (novoSaldo < 0) {
	    this.saldo = 0;
	    return;
	}

	this.saldo = novoSaldo;
    }

    public void depositar(double valor) {
	this.saldo += valor;
    }

    public double verificaSaldo() {
	return this.saldo;
    }

    public String recuperaDadosParaImpressao() {
	return String.format("Nome do titular: %s%n"
			     + "Número da conta: %d%n"
			     + "Agência: %d%n"
			     + "Saldo: %.2f%n"
			     + "Data de abertura: %s%n",
			     this.nomeDoTitular,
			     this.numero,
			     this.agencia,
			     this.saldo,
			     this.dataDeAbertura);
    }

    public int getIdentificador() {
	return this.identificador;
    }
}

class UniqueIdGenerator {

    private int availableId = 0;

    public int generateId() {
	return this.availableId++;
    }
}

class TestaConta {

    public static void main(String... args) {
	testeSaque();
	testeDeposito();
	testeLimite();
	testeIdentificadores();
    }

    private static void testeIdentificadores() {

	ContaBancaria a = new ContaBancaria();
	ContaBancaria b = new ContaBancaria();

	boolean success = a.getIdentificador() != b.getIdentificador();
	System.out.printf("[%s] identificadores únicos por conta%n", success ? "SUCCESS" : "FAILURE");
    }

    private static void testeSaque() {

	ContaBancaria conta = new ContaBancaria();

	conta.depositar(100);
	conta.sacar(conta.verificaSaldo());

	boolean sucesso = conta.verificaSaldo() == 0;
	System.out.printf("[%s] saque funcional%n", sucesso ? "SUCCESS" : "FAILURE");
    }

    private static void testeDeposito() {

	ContaBancaria conta = new ContaBancaria();

	double saldoAntigo = conta.verificaSaldo();
	double DEPOSITO = 100;
	conta.depositar(DEPOSITO);

	boolean sucesso = conta.verificaSaldo() == (saldoAntigo + DEPOSITO);
	System.out.printf("[%s] deposito funcional%n", sucesso ? "SUCCESS" : "FAILURE");
    }

    private static void testeLimite() {

	ContaBancaria conta = new ContaBancaria(ContaBancaria.SAQUE_MAXIMO * 2);

	conta.sacar(ContaBancaria.SAQUE_MAXIMO + 1);

	boolean sucesso = conta.verificaSaldo() == ContaBancaria.SAQUE_MAXIMO;
	System.out.printf("[%s] limite de saque%n", sucesso ? "SUCCESS" : "FAILURE");

    }

}
