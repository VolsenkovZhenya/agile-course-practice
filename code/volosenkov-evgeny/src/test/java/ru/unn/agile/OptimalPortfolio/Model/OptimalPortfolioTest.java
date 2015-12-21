/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Optimalportfolio;

import org.junit.Test;
import  static org.junit.Assert.*;
import java.util.ArrayList;
/**
 *
 * @author Жентос
 */
public class OptimalPortfolioTest {
    private final float delta = 0.0001f;
    private final int STEP_FOR_HUNDRED_PORTFOLIOS = 10;
    @Test
    public void canGetExeptionWhenThereIsNotPortfolio()
    {
        assertEquals(getExceptionMessage(null), "No Portfolios");
    }
    
     @Test
    public void canPortfolioIsEmpty()
    {
        Portfolio portfolioTMP = new Portfolio();
        assertEquals(portfolioTMP.IsEmpty(),true);
    }
    @Test   
    public void canPortfolioIsNotEmpty()
    {
       Portfolio portfolioTMP = arrangeWhenPortfolioIsNotEmpty();
       assertEquals(portfolioTMP.IsEmpty(), false);
    }
    @Test
    public void canGetIdPortfolio()
    {
       Portfolio portfolioTMP = arrangeWhenFindEfficiencyInPortfolio();
       assertEquals(portfolioTMP.GetId(), 256);
    }
    @Test
    public void canFindEfficiencyInPortfolio()
    {
       Portfolio portfolioTMP = arrangeWhenFindEfficiencyInPortfolio();
       assertEquals(portfolioTMP.GetEfficiency(),0.12f, delta);
    }
    
    @Test
    public void canFindRiskInPortfolio()
    {

       Portfolio portfolioTMP = arrangeWhenFindRiskInPortfolio();
       assertEquals(portfolioTMP.GetRisk(), 0.0001f, delta);
    }
    
    @Test
    public void canGetExeptionWhenPortfeliosAreEmpty()
    {
        ArrayList <Portfolio> portfolios = arrangeWhenExeptionWhenPortfeliosAreEmpty();
        assertEquals(getExceptionMessage(portfolios), "All Portfolios are empty");
    }
   
    @Test
    public void canGetWhenOnlyOnePortfolio()
    {
        ArrayList <Portfolio> portfolios;
        portfolios = arrangWhenOnlyOnePortfolio();
        int[] theoreticResult = {1};
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(portfolios), theoreticResult);
    } 
    
    @Test 
    public void canGetWhenFirstPortfolioEqualSecond()
    {
        int result = arrangeWhenFirstPortfolioEqualSecond();
        assertEquals(result, 0);
    }
    
    @Test 
    public void canGetWhenNoDominationFirstPorfolioRiskIsGreaterSecond()
    {
        int result = arrangeWhenNoDominationFirstPorfolioRiskIsGreaterSecond();
        assertEquals(result, 0);
    }
    @Test
    public void canGetWhenNoDominationFirstPorfolioEfficiencyIsGreaterSecond()
    {
        int result = arrangeWhenNoDominationFirstPorfolioEfficiencyIsGreaterSecond();
        assertEquals(result, 0);
    }
     @Test 
    public void canGetWhenFirstPortfolioDominatesSecondWithoutEquals()
    {
        int result = arrangeWhenFirstPortfolioDominatesSecondWithoutEquals();
        assertEquals(result, 1);
    }
    @Test
    public void canGetWhenFirstPortfolioDominatesSecondAndRisksEquals()
    {
        int result = arrangeWhenFirstPortfolioDominatesSecondAndRisksEquals();
        assertEquals(result, 1);
    }
    @Test
    public void canGetWhenFirstPortfolioDominatesSecondAndEfficiencysEquals()
    {
        int result = arrangeWhenFirstPortfolioDominatesSecondAndEfficiencysEquals();
        assertEquals(result, 1);
    }
    @Test
    public void canGetWhenSecondPortfolioDominatesFirstWithoutEquals()
    {
        int result = arrangeWhenSecondPortfolioDominatesFirstWithoutEquals();
        assertEquals(result, -1);
    }
    
    @Test
    public void canGetWhenSecondPortfolioDominatesFirstAndRisksEquals()
    {
        int result = arrangeWhenSecondPortfolioDominatesFirstAndRisksEquals();
        assertEquals(result, -1);
    }
    @Test
    public void canGetWhenSecondPortfolioDominatesFirstAndEfficiencysEquals()
    {
        int result = arrangeWhenSecondPortfolioDominatesFirstAndEfficiencysEquals();
        assertEquals(result, -1);
    }
   //дописать ещё тесты
     @Test
    public void canGetOneOptimalPortfolioOfSeveral()
    {
        int [] theoreticResult = {1};
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(arrangeWhenOneOptimalPortfolioOfSeveral()), theoreticResult);
        
    }
    @Test
    public void canGetOTwoOptimalPortfolioOfSeveral()
    {
        ArrayList <Portfolio> portfolios;
        portfolios = arrangWhenOnlyOnePortfolio();
        int [] theoreticResult = {1, 3};
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(arrangeWhenTwoOptimalPortfolioOfSeveral()), theoreticResult);
        
    }
    
    @Test
    public void canGetWhenTenOptimalPortfolioOfSeveral()
    {
        ArrayList <Portfolio> portfolios;
        portfolios = arrangWhenOnlyOnePortfolio();
        int[]  theoreticResult = new int[10];
        for (int i = 0; i < 10; i++) theoreticResult[i] =i*STEP_FOR_HUNDRED_PORTFOLIOS;
        assertArrayEquals(tryGetIdOfOptimalPortfoliosWithoutException(arrangeWhenTenOptimalPortfolioOfHundred()), theoreticResult);
    }
    
    private String getExceptionMessage(final ArrayList <Portfolio> Portfolios){
        try {
             OptimalPortfolio.GetOptimalPortfolio(Portfolios);
         } catch (Exception ex) {
             return ex.getMessage();
         }
         return "";
      
    }
    
    private int [] tryGetIdOfOptimalPortfoliosWithoutException(final ArrayList <Portfolio> Portfolios)
    {
        try {
             return OptimalPortfolio.GetArrayIdOptimumPortfolios(OptimalPortfolio.GetOptimalPortfolio(Portfolios));
         } catch (Exception ex) {
             return null;
         }
        
    }
    
    private  Portfolio arrangeWhenPortfolioIsNotEmpty()
    {
       float[] share = {0.5f};
       float [][] incomes =  {{0.11f}};
       return new Portfolio(share, incomes, 0);  
    }
    
    private Portfolio arrangeWhenFindEfficiencyInPortfolio()
    {
       float[] share = {0.5f, 0.5f};
       float [][] incomes =  {{0.11f, 0.13f}, {0.15f, 0.09f}};
       return new Portfolio(share, incomes, 256);
    }
    
    private Portfolio arrangeWhenFindRiskInPortfolio()
    {
       float[] share = {0.5f, 0.5f};
       float [][] incomes =  {{0.11f, 0.13f}, {0.15f, 0.08f}}; 
       return new Portfolio(share, incomes, 1);
    }
    
    private ArrayList <Portfolio> arrangeWhenExeptionWhenPortfeliosAreEmpty()
    {
        final ArrayList <Portfolio> portfolios;
        portfolios = new ArrayList<>();
        for( int i = 0; i < 10; i++)
            portfolios.add(new Portfolio());
        return portfolios;
    }
    
    private ArrayList <Portfolio> arrangWhenOnlyOnePortfolio()
    {
        ArrayList <Portfolio> portfolios;
        portfolios = new ArrayList<>();
        final float [] share = { 0.3f, 0.4f, 0.3f};
        float  [][] incomes ={{0.1f, 0.31f, 0.2f}, {0.4f, 0.15f, 0.3f}, { 0.2f, 0.2f, 0.2f}};  
        portfolios.add(new Portfolio(share, incomes, 1));
        return portfolios;
    }
    
    private int arrangeWhenFirstPortfolioEqualSecond()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes ={{0.3f, 0.6f}, {0.3f, 0.3f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 ={{0.6f, 0.3f}, {0.3f, 0.3f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    
    private int arrangeWhenNoDominationFirstPorfolioRiskIsGreaterSecond()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes = {{0.6f, 0.9f}, {0.3f, 0.7f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 ={{0.3f, 0.6f}, {0.3f, 0.3f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    
    private int arrangeWhenNoDominationFirstPorfolioEfficiencyIsGreaterSecond()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes ={{0.3f, 0.6f}, {0.3f, 0.3f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 = {{0.6f, 0.9f}, {0.3f, 0.7f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    private int arrangeWhenFirstPortfolioDominatesSecondWithoutEquals()
    {
        float [] share = { 0.1f, 0.9f};
        float  [][] incomes ={{0.1f, 0.2f}, {0.8f, 0.9f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 = {{0.6f, 0.9f}, {0.3f, 0.7f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    private int arrangeWhenFirstPortfolioDominatesSecondAndRisksEquals()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes ={{0.9f, 0.9f}, {0.9f, 0.9f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 = {{0.6f, 0.6f}, {0.7f, 0.7f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    
    private int arrangeWhenFirstPortfolioDominatesSecondAndEfficiencysEquals()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes = {{0.51f, 0.31f}, {0.8f, 0.8f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 ={{0.7f, 0.9f}, {0.7f, 0.12f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    private int arrangeWhenSecondPortfolioDominatesFirstWithoutEquals()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes = {{0.6f, 0.9f}, {0.3f, 0.7f}};
        float [] share2 = { 0.1f, 0.9f};
        float  [][] incomes2 ={{0.1f, 0.2f}, {0.8f, 0.9f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    private int arrangeWhenSecondPortfolioDominatesFirstAndRisksEquals()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes = {{0.6f, 0.6f}, {0.7f, 0.7f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 ={{0.9f, 0.9f}, {0.9f, 0.9f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    private int arrangeWhenSecondPortfolioDominatesFirstAndEfficiencysEquals()
    {
        float [] share = { 0.5f, 0.5f};
        float  [][] incomes ={{0.7f, 0.9f}, {0.7f, 0.12f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 = {{0.51f, 0.31f}, {0.8f, 0.8f}};
        Portfolio first = new Portfolio(share, incomes, 1);
        Portfolio second = new Portfolio(share2, incomes2, 2);
        OptimalPortfolio TMP = new OptimalPortfolio();
        return TMP.GetRatioOfDominationParreto(first, second);
    }
    private ArrayList <Portfolio> arrangeWhenOneOptimalPortfolioOfSeveral()
    {
        ArrayList <Portfolio> portfolios = new ArrayList <>();
        float [] share = { 0.4f, 0.6f};
        float  [][] incomes ={{0.4f, 0.4f}, {0.9f, 0.6f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 ={{0.3f, 0.9f}, {0.8f, -0.5f}};
        portfolios.add(new Portfolio(share, incomes, 1));
        portfolios.add(new Portfolio(share2, incomes2, 2));
        return portfolios;
    }
    
    private ArrayList <Portfolio> arrangeWhenTwoOptimalPortfolioOfSeveral()
    {
        ArrayList <Portfolio> portfolios = new ArrayList <>();
        float [] share = { 0.4f, 0.6f};
        float  [][] incomes ={{0.4f, 0.4f}, {0.9f, 0.6f}};
        float [] share2 = { 0.5f, 0.5f};
        float  [][] incomes2 ={{0.3f, 0.9f}, {0.8f, -0.5f}};
        portfolios.add(new Portfolio(share, incomes, 1));
        portfolios.add(new Portfolio(share2, incomes2, 2));
        portfolios.add(new Portfolio(share, incomes, 3));
        return portfolios;
    }
    private ArrayList <Portfolio> arrangeWhenTenOptimalPortfolioOfHundred()
    {
        ArrayList <Portfolio> portfolios = new ArrayList <>();
        float [] share = {0.4f, 0.6f};
        float  [][] incomes ={{5.0f, 5.0f}, {5.0f, 5.0f}};
        float [] share2 = new float [10];
        float [][] incomes2 = new float [10][10];
        for(int k = 0; k < 99; k++)
        {
            if(k % STEP_FOR_HUNDRED_PORTFOLIOS == 0)
            {
                portfolios.add(new Portfolio(share, incomes, k));
                continue;
            }
            for( int i = 0; i < 10; i++)
            {
                share2[i] = 0.1f;
                for(int j = 0; j < 10; j++)
                {
                    incomes2[i][j] = (float)(i+j+i*j)/1000.0f;
                }
            }
            portfolios.add(new Portfolio(share2, incomes2, k));
        }
        return portfolios;
    }
}