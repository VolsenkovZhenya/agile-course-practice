/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Optimalportfolio;


public class Portfolio {

    final private float[][] incomes;
    final private float[] share;
    private float efficiency;
    private float risk;
    final private boolean isEmpty;
    private int id;
    public Portfolio() {
        incomes = null;
        share = null;
        efficiency = 0.0f;
        risk = 0.0f;
        isEmpty = true;
        id = 0;
    }

    public Portfolio(final float[] share, final float[][] incomes, int id) {
        if(share != null && incomes != null)
        {
            this.incomes = incomes;
            this.share = share;
            this.id = id;
            efficiency = 0.0f;
            risk = 0.0f;
            isEmpty = false;
            FindRiskAndEfficiency();
        }
        else{
            this.incomes = null;
            this.share = null;
            efficiency = 0.0f;
            risk = 0.0f;
            id = 0;
            isEmpty = true;
        }

    }
       
    public boolean IsEmpty() {
        return isEmpty;
    }
        
    private void FindRiskAndEfficiency() {
        float[] midIncomes = new float[incomes.length];
        float summOfIncomes;
        for (int i = 0; i < incomes.length; i++) {
            summOfIncomes = 0.0f;
            for (int j = 0; j < incomes[i].length; j++) {
                summOfIncomes += incomes[i][j];
            }
            midIncomes[i] = summOfIncomes / incomes[i].length;
        }
        FindEfficiency(midIncomes);
        FindRisk(midIncomes);
    }

    private void FindEfficiency(float[] midIncomes) {
        for (int i = 0; i < incomes.length; i++) {
            efficiency += share[i] * midIncomes[i];
        }
    }

    private float FindCovariationBeetwenIncomes(float[] midIncomes, int incomeOfFirstDocument, int incomeOfSecondDocument) {
        float Covariation = 0;
        for (int i = 0; i < incomes[incomeOfFirstDocument].length; i++) {
            Covariation += incomes[incomeOfFirstDocument][i] * incomes[incomeOfSecondDocument][i];
        }
        Covariation /= incomes[incomeOfFirstDocument].length;
        Covariation += -midIncomes[incomeOfFirstDocument] * midIncomes[incomeOfSecondDocument];
        return Covariation;
    }

    private void FindRisk(float[] midIncomes) {
        for (int i = 0; i < share.length; i++) {
            risk += share[i] * share[i] * FindCovariationBeetwenIncomes(midIncomes, i, i);
            for (int j = i + 1; j < share.length; j++) {
                risk += 2 * share[i] * share[j] * FindCovariationBeetwenIncomes(midIncomes, i, j);
            }
        }
    }
    public float GetEfficiency() {
        return efficiency;
    }
    
    public int GetId()
    {
        return id;
    }
    public float GetRisk() {
        return risk;
    }



}
