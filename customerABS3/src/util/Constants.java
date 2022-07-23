package util;

import com.google.gson.Gson;

public class Constants {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String JHON_DOE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // fxml locations
    public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/chat/client/component/main/chat-app-main.fxml";
    public final static String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/chat/client/component/login/login.fxml";
    public final static String CHAT_ROOM_FXML_RESOURCE_LOCATION = "/chat/client/component/chatroom/chat-room-main.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/web_demo_Web_exploded";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/login";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/userslist";
    public final static String LOAD_FILE = FULL_SERVER_PATH + "/loadFile";
    public final static String RAISE_TIME_LINE = FULL_SERVER_PATH + "/raiseTheTimeLine";
    public final static String GSON_OF_LOGIC = FULL_SERVER_PATH + "/businessLogic";
    public final static String CREATE_NEW_LOAN = FULL_SERVER_PATH + "/submitNewLoan";
    public final static String SCRAMBLE_LOANS = FULL_SERVER_PATH + "/scrambleLoans";
    public final static String PAY_LOAN_FOR_TODAY = FULL_SERVER_PATH + "/PayLoanFeeForTodayServlet";
    public final static String PAY_ALL_FOR_LOAN = FULL_SERVER_PATH + "/PayAllLoanFees";
    public final static String CHARGE_MONEY = FULL_SERVER_PATH + "/ChargeMoneyServlet";
    public final static String WITHDRAW_MONEY = FULL_SERVER_PATH + "/WithdrawMoneyServlet";
    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
