package ru.unn.agile.OptimalPortfolio.Model;
import org.junit.Test;
import  static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
@RunWith(Enclosed.class)
public class OptimalPortfolioTest {
    private static final float DELTA = 0.0001f;
    private static final int STEP_FOR_HUNDRED_PORTFOLIOS = 10;
    @RunWith(Parameterized.class)
    public static class RatioOfDominationParretoTest {
        public RatioOfDominationParretoTest(final float[] shares1, final float[][] incomes1,
                                            final float[] shares2, final float[][] incomes2,
                                            final int correctResult) {
            this.first = new Portfolio(shares1, incomes1);
            this.second = new Portfolio(shares2, incomes2);
            this.correctResult = correctResult;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> testValue() {
            return Arrays.asList(new Object[][]{{null, null, null, null, 0},
                    {null, null, new float[] {1.0f}, new float[][]{{0.5f}}, -1},
                    {new float[] {1.0f}, new float[][]{{0.5f}}, null, null, 1},
                    {new float[]{0.5f, 0.5f}, new float[][]{{0.3f, 0.3f}, {0.3f, 0.6f}},
                     new float[]{0.5f, 0.5f}, new float[][]{{0.6f, 0.3f}, {0.3f, 0.3f}}, 0},
                    {new float[]{0.5f, 0.5f}, new float[][]{{0.6f, 0.9f}, {0.3f, 0.7f}},
                     new float[]{0.5f, 0.5f}, new float[][]{{0.3f, 0.6f}, {0.3f, 0.3f}}, 0},
                    {new float[]{0.1f, 0.9f}, new float[][]{{0.1f, 0.2f}, {0.8f, 0.9f}},
                     new float[]{0.5f, 0.5f}, new float[][]{{0.6f, 0.9f}, {0.3f, 0.7f}}, 1},
                    {new float[]{0.5f, 0.5f}, new float[][]{{0.9f, 0.9f}, {0.9f, 0.9f}},
                     new float[]{0.5f, 0.5f}, new float[][]{{0.6f, 0.6f}, {0.7f, 0.7f}}, 1},
                    {new float[]{0.5f, 0.5f}, new float[][]{{0.6f, 0.9f}, {0.3f, 0.7f}},
                     new float[]{0.1f, 0.9f}, new float[][]{{0.1f, 0.2f}, {0.8f, 0.9f}}, -1},
                    {new float[]{0.5f, 0.5f}, new float[][]{{0.6f, 0.6f}, {0.7f, 0.7f}},
                     new float[]{0.5f, 0.5f}, new float[][]{{0.9f, 0.9f}, {0.9f, 0.9f}}, -1},
                    {new float[]{0.5f, 0.5f}, new float[][]{{0.7f, 0.9f}, {0.7f, 0.12f}},
                     new float[]{0.5f, 0.5f}, new float[][]{{0.51f, 0.31f}, {0.8f, 0.8f}}, -1}});
        }

        @Test
        public void canGetRatioOfDomination() {

            assertEquals(OptimalPortfolio.getRatioOfDominationParreto(first, second),
                    correctResult);
        }

        private Portfolio first;
        private Portfolio second;
        private int correctResult;
    }
    @RunWith(Parameterized.class)
    public static class FindOptimalPortfolioListTest {
        public FindOptimalPortfolioListTest(final ArrayList<Portfolio> portfolios,
                                            final int[] correctResult) {
            this.portfolios = portfolios;
            this.correctResult = correctResult;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> testValue() {
            return Arrays.asList(new Object[][]{
                    {getListWhereOnlyOnePortfolio(), new int[]{1}},
                    {getListWhereOneOptimalPortfolioOfSeveral(), new int[]{1}},
                    {getListWhereTwoOptimalPortfolioOfSeveral(), new int[]{1, 3}},
                    {getListWhenManyPortfolios(), getCorrectResultWhenManyPortfolios()}
            });
        }

        @Test
        public void canGetArrayIdOfOptimalPortfolios() {

            assertArrayEquals(tryGetIdOptimalPortfoliosWithoutException(portfolios),
                    correctResult);
        }

        private ArrayList<Portfolio> portfolios;
        private int[] correctResult;

        private static ArrayList<Portfolio> getListWhereOnlyOnePortfolio() {
            ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
            portfolios.add(new Portfolio(new float[]{0.3f, 0.4f, 0.3f},
                    new float[][]{{0.1f, 0.31f, 0.2f}, {0.4f, 0.15f, 0.3f},
                            {0.2f, 0.2f, 0.2f}}, 1));
            return portfolios;
        }
        private static ArrayList<Portfolio> getListWhereOneOptimalPortfolioOfSeveral() {
            ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
            portfolios.add(new Portfolio(new float[]{0.6f, 0.4f},
                    new float[][]{{0.9f, 0.6f}, {0.4f, 0.4f}}, 1));
            portfolios.add(new Portfolio(new float[]{0.5f, 0.5f},
                    new float[][]{{0.3f, 0.9f}, {0.8f, -0.5f}}, 2));
            return portfolios;
        }

        private static ArrayList<Portfolio> getListWhereTwoOptimalPortfolioOfSeveral() {
            ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
            portfolios.add(new Portfolio(new float[]{0.4f, 0.6f},
                    new float[][]{{0.4f, 0.4f}, {0.9f, 0.6f}}, 1));
            portfolios.add(new Portfolio(new float[]{0.5f, 0.5f},
                    new float[][]{{0.8f, -0.5f}, {0.3f, 0.9f}}, 2));
            portfolios.add(new Portfolio(new float[]{0.4f, 0.6f},
                    new float[][]{{0.4f, 0.4f}, {0.9f, 0.6f}}, 3));
            return portfolios;
        }
        private static ArrayList<Portfolio> getListWhenManyPortfolios() {
            ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
            for (int k = 0; k < 99; k++) {
                if (k % STEP_FOR_HUNDRED_PORTFOLIOS == 0) {
                    portfolios.add(new Portfolio(new float[]{0.4f, 0.6f},
                            new float[][]{{5.0f, 5.0f}, {5.0f, 5.0f}}, k));
                    continue;
                }
                addPortfolioInList(portfolios, k);
            }
            return portfolios;
        }

        private static void addPortfolioInList(final ArrayList<Portfolio> portfolios,
                                               final int idPortfolio) {
            float [] shares = new float [10];
            float [][] incomes = new float [10][10];
            for (int i = 0; i < 10; i++) {
                shares[i] = 0.1f;
                for (int j = 0; j < 10; j++) {
                    incomes[i][j] = (float) (i + j + i * j + idPortfolio) / 1000.0f;
                }
            }
            portfolios.add(new Portfolio(shares, incomes, idPortfolio));
        }

        private static int[] getCorrectResultWhenManyPortfolios() {
            int[]  correctResult = new int[10];
            for (int i = 0; i < 10; i++) {
                correctResult[i] = i * STEP_FOR_HUNDRED_PORTFOLIOS;
            }
            return correctResult;
        }
        private int [] tryGetIdOptimalPortfoliosWithoutException(
                final ArrayList<Portfolio> portfolios) {
            try {
                return OptimalPortfolio.getArrayIdOptimumPortfolios(
                        OptimalPortfolio.getOptimalPortfolios(portfolios));
            } catch (Exception ex) {
                return null;
            }
        }
    }
    public static class PortfolioTest {
        @Test
        public void canDetectWhenPortfolioEmpty() {
            assertTrue(new Portfolio().isEmpty());
        }
        @Test
        public void canDetectWhenPortfolioNoEmpty() {
            assertFalse(arrangePortfolioWhenItNoEmpty().isEmpty());
        }
        @Test
        public void canGetId() {
            assertEquals(new Portfolio(new float[]{1.0f},
                    new float[][]{{0.7f}}, 256).getId(), 256);
        }
        @Test
        public void canFindEfficiency() {
            assertEquals(arrangePortfolioWhenFindEfficiency().getEfficiency(), 0.12f, DELTA);
        }
        @Test
        public void canFindRisk() {
            assertEquals(arrangePortfolioWhenFindRisk().getRisk(), 0.0001f, DELTA);
        }

        private  Portfolio arrangePortfolioWhenItNoEmpty() {
            return new Portfolio(new float[]{0.5f}, new float[][]{{0.11f}});
        }

        private Portfolio arrangePortfolioWhenFindEfficiency() {
            return new Portfolio(new float[]{0.5f, 0.5f},
                    new float[][]{{0.11f, 0.13f}, {0.15f, 0.09f}});
        }


        private Portfolio arrangePortfolioWhenFindRisk() {
            return new Portfolio(new float[]{0.5f, 0.5f},
                    new float[][]{{0.11f, 0.13f}, {0.15f, 0.08f}});
        }
    }
    public static class GettingExeptionTest {
        @Test
        public void canGetExeptionWhenListEmpty() {
            assertEquals(getExceptionMessage(null), "No Portfolios");
        }

        @Test
        public void canGetExeptionWhenAllPortfoliosInListEmpty() {
            assertEquals(getExceptionMessage(arrangeListWhenAllPortfoliosEmpty()),
                    "All Portfolios are empty");
        }


        private String getExceptionMessage(final ArrayList<Portfolio> portfolios) {
            try {
                OptimalPortfolio.getOptimalPortfolios(portfolios);
            } catch (Exception ex) {
                return ex.getMessage();
            }
            return "";
        }


        private ArrayList<Portfolio> arrangeListWhenAllPortfoliosEmpty() {
            final ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
            for (int i = 0; i < 10; i++) {
                portfolios.add(new Portfolio());
            }
            return portfolios;
        }
    }
}


