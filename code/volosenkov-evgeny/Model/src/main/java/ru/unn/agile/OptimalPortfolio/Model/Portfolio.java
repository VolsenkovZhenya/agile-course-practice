
package ru.unn.agile.OptimalPortfolio.Model;

public class Portfolio {
    private final float[][] incomes;
    private final float[] shares;
    private float efficiency;
    private float risk;
    private final boolean isEmpty;
    private  int id;
    public Portfolio() {
        incomes = null;
        shares = null;
        efficiency = 0.0f;
        risk = 0.0f;
        isEmpty = true;
        id = 0;
    }
    public Portfolio(final float[] shares, final float[][] incomes, final int id) {
        if (shares == null || incomes == null) {
            this.incomes = null;
            this.shares = null;
            efficiency = 0.0f;
            risk = 0.0f;
            this.id = 0;
            isEmpty = true;
            return;
        }
        this.incomes = incomes;
        this.shares = shares;
        this.id = id;
        efficiency = 0.0f;
        risk = 0.0f;
        isEmpty = false;
        findRiskAndEfficiency();

    }
    public Portfolio(final float[] shares, final float[][] incomes) {
        if (shares == null || incomes == null) {
            this.incomes = null;
            this.shares = null;
            efficiency = 0.0f;
            risk = 0.0f;
            isEmpty = true;
            return;
        }
        this.incomes = incomes;
        this.shares = shares;
        efficiency = 0.0f;
        risk = 0.0f;
        isEmpty = false;
        findRiskAndEfficiency();

    }
    public boolean isEmpty() {
        return isEmpty;
    }
    public float getEfficiency() {
        return efficiency;
    }
    public int getId() {
        return id;
    }
    public float getRisk() {
        return risk;
    }
    private void findRiskAndEfficiency() {
        final float[] midIncomes = new float[incomes.length];
        float summOfIncomes;
        for (int i = 0; i < incomes.length; i++) {
            summOfIncomes = 0.0f;
            for (int j = 0; j < incomes[i].length; j++) {
                summOfIncomes += incomes[i][j];
            }
            midIncomes[i] = summOfIncomes / incomes[i].length;
        }
        findEfficiency(midIncomes);
        findRisk(midIncomes);
    }
    private void findEfficiency(final float[] midIncomes) {
        for (int i = 0; i < incomes.length; i++) {
            efficiency += shares[i] * midIncomes[i];
        }
    }
    private  float findCovariationBeetwenIncomes(final float[] midIncomes,
             final int indexFirstDocument, final int indexSecondDocument) {
        float covariation = 0;
        for (int i = 0; i < incomes[indexFirstDocument].length; i++) {
            covariation += incomes[indexFirstDocument][i] * incomes[indexSecondDocument][i];
        }
        covariation /= incomes[indexFirstDocument].length;
        covariation += -midIncomes[indexFirstDocument] * midIncomes[indexSecondDocument];
        return covariation;
    }
    private void findRisk(final float[] midIncomes) {
        for (int i = 0; i < shares.length; i++) {
            risk += shares[i] * shares[i] * findCovariationBeetwenIncomes(midIncomes, i, i);
            for (int j = i + 1; j < shares.length; j++) {
                risk += 2 * shares[i] * shares[j] * findCovariationBeetwenIncomes(midIncomes, i, j);
            }
        }
    }

}
