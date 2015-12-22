
package ru.unn.agile.OptimalPortfolio.Model;

public class Portfolio {
    private final float[][] incomes;
    private final float[] share;
    private float efficiency;
    private float risk;
    private final boolean isEmpty;
    private  int id;
    public Portfolio() {
        incomes = null;
        share = null;
        efficiency = 0.0f;
        risk = 0.0f;
        isEmpty = true;
        id = 0;
    }
    public Portfolio(final float[] share, final float[][] incomes, final int id) {
        if (share == null || incomes == null) {
            this.incomes = null;
            this.share = null;
            efficiency = 0.0f;
            risk = 0.0f;
            this.id = 0;
            isEmpty = true;
            return;
        }
        this.incomes = incomes;
        this.share = share;
        this.id = id;
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
            efficiency += share[i] * midIncomes[i];
        }
    }
    private  float findCovariationBeetwenIncomes(final float[] midIncomes,
             final int incomeOfFirstDocument, final int incomeOfSecondDocument) {
        float covariation = 0;
        for (int i = 0; i < incomes[incomeOfFirstDocument].length; i++) {
            covariation += incomes[incomeOfFirstDocument][i] * incomes[incomeOfSecondDocument][i];
        }
        covariation /= incomes[incomeOfFirstDocument].length;
        covariation += -midIncomes[incomeOfFirstDocument] * midIncomes[incomeOfSecondDocument];
        return covariation;
    }
    private void findRisk(final float[] midIncomes) {
        for (int i = 0; i < share.length; i++) {
            risk += share[i] * share[i] * findCovariationBeetwenIncomes(midIncomes, i, i);
            for (int j = i + 1; j < share.length; j++) {
                risk += 2 * share[i] * share[j] * findCovariationBeetwenIncomes(midIncomes, i, j);
            }
        }
    }

}
