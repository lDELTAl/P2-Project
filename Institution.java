public class Institution extends Customer {
    private String commercialRegistry;
    private double discountPercentage;

    public Institution(String id, String name, String address, String phoneNumber, String commercialRegistry, double discountPercentage) {
        super(id, name, address, phoneNumber);
        this.commercialRegistry = commercialRegistry;
        this.discountPercentage = discountPercentage;

    }

    public String getCommercialRegistry() {
        return commercialRegistry;
    }

    public void setCommercialRegistry(String commercialRegistry) {
        this.commercialRegistry = commercialRegistry;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    @Override
    public double getDiscountRate(int rentalDays) {
        // خصم ابتكاري: إذا كانت مدة الإيجار أكثر من 7 أيام، تمنح المؤسسة خصماً إضافياً 5%
        if (rentalDays > 7) {
            System.out.println("🎉 [Promo Applied]: 5% extra discount granted to the institution for long-term rental!");
            return this.getDiscountPercentage() + 0.05;
        }
        // خلاف ذلك، تأخذ المؤسسة نسبتها الأساسية فقط
        return this.getDiscountPercentage();
    }
}
