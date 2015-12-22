package ru.unn.agile.OptimalPortfolio.Model;

import org.junit.Test;
import  static org.junit.Assert.*;
import java.util.ArrayList;

public class OptimalPortfolioTest {
    private static final float DELTA = 0.0001f;
    private static final int STEP_FOR_HUNDRED_PORTFOLIOS = 10;

    @Test
    public void canGetExeptionWhenThereIsNotPortfolio() {
        assertEquals(getExceptionMessage(null), "No Portfolios");
    }
    @Test
    public void canPortfolioIsEmpty() {
        Portfolio portfolioTMP = new Portfolio();
        assertEquals(portfolioTMP.isEmpty(), true);
    }
    @Test
    public void canPortfolioIsNotEmpty() {
       Portfolio portfolioTMP = arrangeWhenPortfolioIsNotEmpty();
       assertEquals(portfolioTMP.isEmpty(), false);
    }
    @Test
    public void canGetIdPortfolio() {
       Portfolio portfolioTMP = arrangeWhenFindEfficiencyInPortfolio();
       assertEquals(portfolioTMP.getId(), 256);
    }
    @Test
    public void canFindEfficiencyInPortfolio() {
       Portfolio portfolioTMP = arrangeWhenFindEfficiencyInPortfolio();
       assertEquals(portfolioTMP.getEfficiency(), 0.12f, DELTA);
    }
    @Test
    public void canFindRiskInPortfolio() {
       assertEquals(arrangeWhenFindRiskInPortfolio().getRisk(), 0.0001f, DELTA);
    }
    @Test
    public void canGetExeptionWhenPortfeliosAreEmpty() {
        ArrayList<Portfolio> portfolios = arrangeWhenExeptionWhenPortfeliosAreEmpty();
        assertEquals(getExceptionMessage(portfolios), "All Portfolios are empty");
    }
    @Test
    public void canGetWhenOnlyOnePortfolio() {
        ArrayList<Portfolio> portfolios;
        portfolios = arrangWhenOnlyOnePortfolio();
        int[] theoreticResult = {1};
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(portfolios),
                theoreticResult);
    }
    @Test
    public void canGetWhenFirstPortfolioEqualSecond() {
        assertEquals(arrangeWhenFirstPortfolioEqualSecond(), 0);
    }
    @Test
    public void canGetWhenNoDominationFirstPorfolioRiskIsGreaterSecond() {
        assertEquals(arrangeWhenNoDominationFirstPorfolioRiskIsGreaterSecond(), 0);
    }
    @Test
    public void canGetWhenNoDominationFirstPorfolioEfficiencyIsGreaterSecond() {
        assertEquals(arrangeWhenNoDominationFirstPorfolioEfficiencyIsGreaterSecond(), 0);
    }
    @Test
    public void canGetWhenFirstPortfolioDominatesSecondWithoutEquals() {
        assertEquals(arrangeWhenFirstPortfolioDominatesSecondWithoutEquals(), 1);
    }
    @Test
    public void canGetWhenFirstPortfolioDominatesSecondAndRisksEquals() {
        assertEquals(arrangeWhenFirstPortfolioDominatesSecondAndRisksEquals(), 1);
    }
    @Test
    public void canGetWhenFirstPortfolioDominatesSecondAndEfficiencysEquals() {
        assertEquals(arrangeWhenFirstPortfolioDominatesSecondAndEfficiencysEquals(), 1);
    }
    @Test
    public void canGetWhenSecondPortfolioDominatesFirstWithoutEquals() {
        assertEquals(arrangeWhenSecondPortfolioDominatesFirstWithoutEquals(), -1);
    }
    @Test
    public void canGetWhenSecondPortfolioDominatesFirstAndRisksEquals() {
        assertEquals(arrangeWhenSecondPortfolioDominatesFirstAndRisksEquals(), -1);
    }
    @Test
    public void canGetWhenSecondPortfolioDominatesFirstAndEfficiencysEquals() {
        assertEquals(arrangeWhenSecondPortfolioDominatesFirstAndEfficiencysEquals(), -1);
    }
    @Test
    public void canGetOneOptimalPortfolioOfSeveral() {
        int [] theoreticResult = {1};
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(
                arrangeWhenOneOptimalPortfolioOfSeveral()), theoreticResult);
    }
    @Test
    public void canGetOTwoOptimalPortfolioOfSeveral() {
        int [] theoreticResult = {1, 3};
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(
                arrangeWhenTwoOptimalPortfolioOfSeveral()), theoreticResult);
    }
    @Test
    public void canGetWhenTenOptimalPortfolioOfSeveral() {
        int[]  theoreticResult = new int[10];
        for (int i = 0; i < 10; i++) {
            theoreticResult[i] = i * STEP_FOR_HUNDRED_PORTFOLIOS;
        }
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(
                arrangeWhenTenOptimalPortfolioOfHundred()), theoreticResult);
    }
    private String getExceptionMessage(final ArrayList<Portfolio> portfolios) {
        try {
             OptimalPortfolio.getOptimalPortfolio(portfolios);
         } catch (Exception ex) {
             return ex.getMessage();
         }
         return "";
    }

    private int [] tryGetIdOfOptimalPortfoliosWithoutException(
            final ArrayList<Portfolio> portfolios) {
        try {
             return OptimalPortfolio.getArrayIdOptimumPortfolios(
                     OptimalPortfolio.getOptimalPortfolio(portfolios));
         } catch (Exception ex) {
             return null;
         }
    }
    private  Portfolio arrangeWhenPortfolioIsNotEmpty() {
       float[] share = {0.5f};
       float [][] incomes =  {{0.11f}};
       return new Portfolio(share, incomes, 0);
    }

    private Portfolio arrangeWhenFindEfficiencyInPortfolio() {
       float[] share = {0.5f, 0.5f};
       float [][] incomes =  {{0.11f, 0.13f}, {0.15f, 0.09f}};
       return new Portfolio(share, incomes, 256);
    }

    private Portfolio arrangeWhenFindRiskInPortfolio() {
       float[] share = {0.5f, 0.5f};
       float [][] incomes =  {{0.11f, 0.13f}, {0.15f, 0.08f}};
        return new Portfolio(share, incomes, 1);
    }

    private ArrayList<Portfolio> arrangeWhenExeptionWhenPortfeliosAreEmpty() {
        final ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
        for (int i = 0; i < 10; i++) {
            portfolios.add(new Portfolio());
        }
        return portfolios;
    }

    private ArrayList<Portfolio> arrangWhenOnlyOnePortfolio() {
        ArrayList<Portfolio> portfolios;
        portfolios = new ArrayList<Portfolio>();
        final float [] share = {0.3f, 0.4f, 0.3f};
        float  [][] incomes = {{0.1f, 0.31f, 0.2f}, {0.4f, 0.15f, 0.3f}, {0.2f, 0.2f, 0.2f}};
        portfolios.add(new Portfolio(share, incomes, 1));
        return portfolios;
    }

    private int arrangeWhenFirstPortfolioEqualSecond() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.3f, 0.3f}, {0.3f, 0.6f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.6f, 0.3f}, {0.3f, 0.3f}};
        Portfolio first = new Portfolio(share, incomes, 7);
        Portfolio second = new Portfolio(share2, incomes2, 12);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenNoDominationFirstPorfolioRiskIsGreaterSecond() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.6f, 0.9f}, {0.3f, 0.7f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.3f, 0.6f}, {0.3f, 0.3f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenNoDominationFirstPorfolioEfficiencyIsGreaterSecond() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.3f, 0.6f}, {0.3f, 0.3f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.3f, 0.7f}, {0.6f, 0.9f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenFirstPortfolioDominatesSecondWithoutEquals() {
        float [] share = {0.1f, 0.9f};
        float  [][] incomes = {{0.1f, 0.2f}, {0.8f, 0.9f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.6f, 0.9f}, {0.3f, 0.7f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenFirstPortfolioDominatesSecondAndRisksEquals() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.9f, 0.9f}, {0.9f, 0.9f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.6f, 0.6f}, {0.7f, 0.7f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenFirstPortfolioDominatesSecondAndEfficiencysEquals() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.51f, 0.31f}, {0.8f, 0.8f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.7f, 0.9f}, {0.7f, 0.12f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenSecondPortfolioDominatesFirstWithoutEquals() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.6f, 0.9f}, {0.3f, 0.7f}};
        float [] share2 = {0.1f, 0.9f};
        float  [][] incomes2 = {{0.1f, 0.2f}, {0.8f, 0.9f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenSecondPortfolioDominatesFirstAndRisksEquals() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.6f, 0.6f}, {0.7f, 0.7f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.9f, 0.9f}, {0.9f, 0.9f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private int arrangeWhenSecondPortfolioDominatesFirstAndEfficiencysEquals() {
        float [] share = {0.5f, 0.5f};
        float  [][] incomes = {{0.7f, 0.9f}, {0.7f, 0.12f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.51f, 0.31f}, {0.8f, 0.8f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        return OptimalPortfolio.getRatioOfDominationParreto(first, second);
    }

    private ArrayList<Portfolio> arrangeWhenOneOptimalPortfolioOfSeveral() {
        ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
        float [] share = {0.6f, 0.4f};
        float  [][] incomes = {{0.9f, 0.6f}, {0.4f, 0.4f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.3f, 0.9f}, {0.8f, -0.5f}};
        portfolios.add(new Portfolio(share, incomes, 1));
        portfolios.add(new Portfolio(share2, incomes2, 2));
        return portfolios;
    }

    private ArrayList<Portfolio> arrangeWhenTwoOptimalPortfolioOfSeveral() {
        ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
        float [] share = {0.4f, 0.6f};
        float  [][] incomes = {{0.4f, 0.4f}, {0.9f, 0.6f}};
        float [] share2 = {0.5f, 0.5f};
        float  [][] incomes2 = {{0.8f, -0.5f}, {0.3f, 0.9f}};
        portfolios.add(new Portfolio(share, incomes, 1));
        portfolios.add(new Portfolio(share2, incomes2, 2));
        portfolios.add(new Portfolio(share, incomes, 3));
        return portfolios;
    }

    private ArrayList<Portfolio> arrangeWhenTenOptimalPortfolioOfHundred() {
        ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
        float [] share = {0.4f, 0.6f};
        float  [][] incomes = {{5.0f, 5.0f}, {5.0f, 5.0f}};

        for (int k = 0; k < 99; k++) {
            if (k % STEP_FOR_HUNDRED_PORTFOLIOS == 0) {
                portfolios.add(new Portfolio(share, incomes, k));
                continue;
            }
            fillPortfolio(portfolios, k);
        }
        return portfolios;
    }

    private void fillPortfolio(final ArrayList<Portfolio> portfolios, final int idPortfolio) {
        float [] share = new float [10];
        float [][] incomes = new float [10][10];
        for (int i = 0; i < 10; i++) {
            share[i] = 0.1f;
            for (int j = 0; j < 10; j++) {
                incomes[i][j] = (float) (i + j + i * j) / 1000.0f;
            }
        }
        portfolios.add(new Portfolio(share, incomes, idPortfolio));
    }
}
