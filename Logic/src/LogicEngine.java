
import java.util.List;

public class LogicEngine implements LogicEngineActions {

    @Override
    public boolean loadSystemDetailsFromFile(String filePath) {
        return false;
    }

    @Override
    public String getDataAboutLoansAndTheirStatus() {
        return null;
    }

    @Override
    public String getClientsData() {
        return null;
    }

    @Override
    public boolean putMoneyInAccount(int moneyToLoad, int client) {
        return false;
    }

    @Override
    public boolean takeMoneyFromAccount(int moneyToTake, int client) {
        return false;
    }

    @Override
    public boolean SchedulingLoansToClient(int client, double moneyToInvest, List<String> categories, double minimumInterestForTimeUnit, int minimumTotalTimeUnitsForInvestment) {
        return false;
    }

    @Override
    public boolean RaiseTheTimeLine() {
        return false;
    }
}


