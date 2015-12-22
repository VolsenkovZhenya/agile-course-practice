package ru.unn.agile.OptimalPortfolio.Model;

import java.util.ArrayList;

public final class OptimalPortfolio {
    private static final int EQUIVALENCE_PORTFOLIOS = 0;
    private static final int FIRST_PORTFOLIO_DOMINATE = 1;
    private static final int SECOND_PORTFOLIO_DOMINATE = -1;
    private static final float DELTA = 0.001f;
    public static ArrayList<Portfolio> getOptimalPortfolio(final ArrayList<Portfolio> portfolios)
            throws Exception {
        if (portfolios == null || portfolios.isEmpty()) {
           throw new Exception("No Portfolios");
        }
        final ArrayList<Portfolio> optimumPortfolios = new ArrayList<Portfolio>();
        if (findOptimalPortfolioParreto(portfolios, optimumPortfolios)) {
            throw new Exception("All Portfolios are empty");
        }
        return optimumPortfolios;
    }
    public static int [] getArrayIdOptimumPortfolios(
            final ArrayList<Portfolio> optimumPortfolios) {
        int [] arrayIdOfOptimalPortfolios = new int[optimumPortfolios.size()];
        for (int i = 0; i < optimumPortfolios.size(); i++) {
            arrayIdOfOptimalPortfolios[i] = optimumPortfolios.get(i).getId();
        }
        return arrayIdOfOptimalPortfolios;
    }
    public static int getRatioOfDominationParreto(final Portfolio first, final Portfolio second) {
        boolean riskEquality = Math.abs(first.getRisk() - second.getRisk()) < DELTA;
        boolean efficiencyEquality = Math.abs(first.getEfficiency()
                - second.getEfficiency()) < DELTA;
        boolean dominationRiskFirstPortfolio = (second.getRisk() - first.getRisk()) >= DELTA;
        boolean dominationEfficiencyFirstPortfolio = (first.getEfficiency()
                - second.getEfficiency()) >= DELTA;
        if (dominationRiskFirstPortfolio && !dominationEfficiencyFirstPortfolio
            && !efficiencyEquality || riskEquality && efficiencyEquality
            || !dominationRiskFirstPortfolio && !riskEquality
            && dominationEfficiencyFirstPortfolio) {
            return EQUIVALENCE_PORTFOLIOS;
        }
        if (dominationRiskFirstPortfolio && dominationEfficiencyFirstPortfolio
            || dominationRiskFirstPortfolio && efficiencyEquality
            || riskEquality && dominationEfficiencyFirstPortfolio) {
            return FIRST_PORTFOLIO_DOMINATE;
        }
        return SECOND_PORTFOLIO_DOMINATE;
    }
    private static boolean findOptimalPortfolioParreto(final ArrayList<Portfolio> portfolios,
                           final ArrayList<Portfolio> optimumPortfolios) {
        boolean allPortfoliosAreEmpty = true;
        for (int i = 0; i < portfolios.size(); i++) {
            if (portfolios.get(i).isEmpty()) {
                continue;
            } else {
                allPortfoliosAreEmpty = false;
            }
            if (optimumPortfolios.isEmpty()) {
                optimumPortfolios.add(portfolios.get(i));
                continue;
            }
            addingToOptimalPortfolios(optimumPortfolios, portfolios.get(i));
        }
        return allPortfoliosAreEmpty;
    }
    private static void addingToOptimalPortfolios(final ArrayList<Portfolio> optimumPortfolios,
                  final Portfolio addingPortfolio) {
        int sizeOptimumPortfolio = optimumPortfolios.size();
        boolean canChange = false;
        boolean canAdding = false;
        for (int j = 0; j < sizeOptimumPortfolio; j++) {
            int stateDomination = getRatioOfDominationParreto(addingPortfolio,
                    optimumPortfolios.get(j));
            if (stateDomination == SECOND_PORTFOLIO_DOMINATE) {
                break;
            }
            if (stateDomination == FIRST_PORTFOLIO_DOMINATE) {
                if (!canChange) {
                    canChange = true;
                    optimumPortfolios.set(j, addingPortfolio);
                    continue;
                }
                optimumPortfolios.remove(j);
                continue;
            }
            if (stateDomination == EQUIVALENCE_PORTFOLIOS && !canAdding) {
                optimumPortfolios.add(addingPortfolio);
                canAdding = true;
            }
        }
    }
    private  OptimalPortfolio() {
    }
}
