package Optimalportfolio;
import java.util.ArrayList;

public class OptimalPortfolio {

    public static final int EQUIVALENCE_PORTFOLIOS = 0;
    public static final int FIRST_PORTFOLIO_DOMINATE = 1;
    public static final int SECOND_PORTFOLIO_DOMINATE = -1;
    public static final float DELTA = 0.001f;
    public OptimalPortfolio(){
    }
    public  static ArrayList <Portfolio> GetOptimalPortfolio(final ArrayList <Portfolio> portfolios) throws Exception
    {
        if( portfolios == null || portfolios.isEmpty() ) 
        {
           throw new Exception("No Portfolios");
        }
        ArrayList <Portfolio> optimumPortfolios;
        optimumPortfolios = new ArrayList <>();
        if(FindOptimalPortfolioParreto(portfolios, optimumPortfolios))
        {
            throw new Exception("All Portfolios are empty");
        }
        return optimumPortfolios;
        
    }
    public static int [] GetArrayIdOptimumPortfolios( ArrayList <Portfolio> optimumPortfolios)
    {
        int [] ArrayIdOfOptimalPortfolios= new int[optimumPortfolios.size()];
        for( int i = 0; i <optimumPortfolios.size(); i++)
            ArrayIdOfOptimalPortfolios[i] = optimumPortfolios.get(i).GetId();
        return ArrayIdOfOptimalPortfolios;
    }
    
    public static int GetRatioOfDominationParreto(Portfolio first, Portfolio second)
    {
        boolean riskEquality = Math.abs(first.GetRisk() - second.GetRisk()) < DELTA;
        boolean efficiencyEquality = Math.abs(first.GetEfficiency() - second.GetEfficiency()) < DELTA;
        boolean dominationRiskFirstPortfolio = (second.GetRisk() - first.GetRisk()) >= DELTA;
        boolean dominationEfficiencyFirstPortfolio = (first.GetEfficiency() - second.GetEfficiency()) >= DELTA;
        if((dominationRiskFirstPortfolio && !dominationEfficiencyFirstPortfolio && !efficiencyEquality)
          || (!dominationRiskFirstPortfolio && !riskEquality &&  dominationEfficiencyFirstPortfolio)||
          (riskEquality && efficiencyEquality))
        {
            return EQUIVALENCE_PORTFOLIOS;
        }
        
        if((dominationRiskFirstPortfolio && dominationEfficiencyFirstPortfolio) ||
           (dominationRiskFirstPortfolio && efficiencyEquality) ||
           (riskEquality && dominationEfficiencyFirstPortfolio))
        {
            return FIRST_PORTFOLIO_DOMINATE;
        }
        return SECOND_PORTFOLIO_DOMINATE;
    }
    private static boolean FindOptimalPortfolioParreto(final ArrayList <Portfolio> portfolios, ArrayList <Portfolio> optimumPortfolios )
    {
        boolean allPortfoliosAreEmpty = true;
        
        for(int i = 0; i < portfolios.size(); i++)
        {
            if(portfolios.get(i).IsEmpty()){
                continue;
            }
            else
                allPortfoliosAreEmpty = false;
            if(optimumPortfolios.isEmpty())
            {
                optimumPortfolios.add(portfolios.get(i));
                continue;
            }
            AddingToOptimalPortfolios(optimumPortfolios, portfolios.get(i));
            
            
        }
        return allPortfoliosAreEmpty;
    }
    private static void AddingToOptimalPortfolios(ArrayList <Portfolio> optimumPortfolios, Portfolio addingPortfolio)
    {
        int sizeOptimumPortfolio = optimumPortfolios.size();
        boolean canChange = false;
        boolean canAdding = false;
        for( int j = 0; j < sizeOptimumPortfolio; j++)
        {           
            int stateDomination = GetRatioOfDominationParreto(addingPortfolio, optimumPortfolios.get(j));
            if(stateDomination == SECOND_PORTFOLIO_DOMINATE) break;
            if(stateDomination == FIRST_PORTFOLIO_DOMINATE)
            {
                if(!canChange)
                {
                   optimumPortfolios.set(j, addingPortfolio); 
                   canChange = true;
                }
                else
                {
                      optimumPortfolios.remove(j);
                }
                continue;
            }
            if((stateDomination == EQUIVALENCE_PORTFOLIOS)&&!canAdding)
            {
                optimumPortfolios.add(addingPortfolio);
                canAdding = true;
            }
        }
    }
}


