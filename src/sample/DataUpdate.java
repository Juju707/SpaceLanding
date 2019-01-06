package sample;

import javafx.collections.ObservableList;

public class DataUpdate implements Observer {
    private ObservableList<MovementParameters> data;

    public DataUpdate(ObservableList<MovementParameters> data) {
        this.data = data;
    }

    @Override
    public void update(MovementParameters movementParameters) {

        data.add(movementParameters);
    }
}
