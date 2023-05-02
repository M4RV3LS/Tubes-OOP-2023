package Sim;

public enum Job {
    BADUT_SULAP("Badut Sulap", 15),
    KOKI("Koki", 30),
    POLISI("Polisi", 35),
    PROGRAMMER("Programmer", 45),
    DOKTER("Dokter", 50);

    private final String name;
    private final int dailySalary;

    Job(String name, int dailySalary) {
        this.name = name;
        this.dailySalary = dailySalary;
    }

    public String getName() {
        return name;
    }

    public int getDailySalary() {
        return dailySalary;
    }
}

