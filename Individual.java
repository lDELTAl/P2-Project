import java.time.LocalDate;
public class Individual extends Customer {
    private String licenseNumber;
    private java.time.LocalDate birthDate;

    public Individual(String id, String name, String address, String phoneNumber, String licenseNumber, LocalDate birthDate) {
        super(id, name, address, phoneNumber);
        this.licenseNumber = licenseNumber;
        this.birthDate = birthDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    @Override
    public double getDiscountRate(int rentalDays) {
        return 0; // الأفراد دائماً 0 خصم كما هو مطلوب في الملف
    }
}
