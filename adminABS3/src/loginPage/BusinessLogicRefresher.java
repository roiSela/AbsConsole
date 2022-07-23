package loginPage;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import model.Bank;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.Constants.GSON_INSTANCE;


public class BusinessLogicRefresher extends TimerTask {

    //my stuff:
    Bank currentLogic;
    private loginPageController mainController;
    //aviad's:
    private final Consumer<String> httpRequestLoggerConsumer;

    private final IntegerProperty chatVersion;
    private final BooleanProperty shouldUpdate;
    private int requestNumber;



    public BusinessLogicRefresher(loginPageController mainController){
        this.httpRequestLoggerConsumer = null;
        this.chatVersion = null;
        this.shouldUpdate = null;
        requestNumber = 0;
        this.mainController = mainController;
    }
    public BusinessLogicRefresher(IntegerProperty chatVersion, BooleanProperty shouldUpdate, Consumer<String> httpRequestLoggerConsumer) {
        this.httpRequestLoggerConsumer = httpRequestLoggerConsumer;
        this.chatVersion = chatVersion;
        this.shouldUpdate = shouldUpdate;
        requestNumber = 0;
    }

    @Override
    public void run() {
        final int finalRequestNumber = ++requestNumber;

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.GSON_OF_LOGIC)
                .newBuilder()
                .build()
                .toString();


        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Something went wrong with Chat Request # " + finalRequestNumber);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String rawBody = response.body().string();
                    System.out.println("Response of Chat Request # " + finalRequestNumber + ": " + rawBody);
                    currentLogic = GSON_INSTANCE.fromJson(rawBody, Bank.class);
                    //tell main to update logic:
                    Platform.runLater(() -> {
                        mainController.updateLogic(currentLogic);
                    });
                } else {
                    System.out.println("Something went wrong with Request # " + finalRequestNumber + ". Code is " + response.code());
                }
            }
        });

    }

}
