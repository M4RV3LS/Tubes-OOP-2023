package Bagian;


public enum Job {
    BADUT_SULAP("Badut Sulap", 15),
    KOKI("Koki", 30),
    POLISI("Polisi", 35),
    PROGRAMMER("Programmer", 45),
    DOKTER("Dokter", 50);

    private String name;
    private int gaji;

    Job(String name, int gaji) {
        this.name = name;
        this.gaji = gaji;
    }

    public String getName() {
        return name;
    }

    public int getGaji() {
        return gaji;
    }
}


