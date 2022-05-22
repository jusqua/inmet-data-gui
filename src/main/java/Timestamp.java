/**
 * Enumerate values set LocalDate.minusDays between today and target
 * FUTURE and TODAY has the same values but different proposes
 */
public enum Timestamp
{
    TODAY(0, "Hoje"),
    YESTERDAY(1, "Desde ontem"),
    WEEK(7, "Uma semana atrás"),
    TWOWEEKS(14, "Duas semanas atrás"),
    MONTH(30, "Um mês atrás");

    private int daysAgo;
    private String text;
    /**
     * Setup Timestamp
     * @param daysAgo
     * @param name
     */
    Timestamp(int daysAgo, String text)
    {
        this.daysAgo = daysAgo;
        this.text = text;
    }

    /**
     * Get daysAgo
     * @return daysAgo
     */
    public int getDaysAgo()
    {
        return this.daysAgo;
    }

    /**
     * Get text
     * @return text
     */
    public String getText()
    {
        return this.text;
    }
}
