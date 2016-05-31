package br.ufrn.baseballeliminationgraph.controller;

import br.ufrn.baseballelimination.exception.TimeNaoEncontradoException;
import br.ufrn.baseballelimination.logica.BaseballElimination;
import br.ufrn.baseballeliminationgraph.Main;
import br.ufrn.baseballeliminationgraph.model.Time;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class InicialController {

    private List<String> pastas;
    private BaseballElimination baseballElimination;

    @FXML
    public TextArea console;
    @FXML
    public ChoiceBox selecao;
    @FXML
    public TableView tabela;

    @FXML
    protected void initialize() {
        File file = new File(Main.SUB_PASTA);
        popularConsole();
        popularTabela();
        popularSelecao(file);
    }

    private void popularConsole() {
        console.appendText("Terminal:\n");
        console.setEditable(false);

        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                Platform.runLater(() -> console.appendText(String.valueOf((char) b)));
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    private void popularTabela() {
        TableColumn time = new TableColumn("Time");
        TableColumn ganhos = new TableColumn("Ganhos");
        TableColumn perdidos = new TableColumn("Perdidos");
        TableColumn restantes = new TableColumn("Restantes");
        TableColumn jogos = new TableColumn("Jogos");

        time.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ganhos.setCellValueFactory(new PropertyValueFactory<>("jogosGanhos"));
        perdidos.setCellValueFactory(new PropertyValueFactory<>("jogosPerdidos"));
        restantes.setCellValueFactory(new PropertyValueFactory<>("jogosRestantes"));
        jogos.setCellValueFactory(new PropertyValueFactory<>("jogos"));

        tabela.getColumns().addAll(time, ganhos, perdidos, restantes, jogos);

        tabela.setEditable(false);
        tabela.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    Time timeSelecionado = (Time) tabela.getSelectionModel().getSelectedItem();
                    try {
                        boolean resultado = baseballElimination.verificaEliminacao(timeSelecionado.getNome());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Resultado");
                        alert.setHeaderText(null);
                        alert.setContentText("Time " + timeSelecionado.getNome() + (resultado ? " será " : " não será ") + "eliminado");

                        alert.showAndWait();
                    } catch (TimeNaoEncontradoException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void popularSelecao(final File folder) {
        selecao.setTooltip(new Tooltip("Selecione um arquivo de testes"));

        pastas = new ArrayList<>();

        for (final File fileEntry : folder.listFiles()) {
            pastas.add(fileEntry.getName());
        }
        selecao.setItems(FXCollections.observableArrayList(pastas));

        selecao.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                tabela.getItems().clear();
                baseballElimination = new BaseballElimination(Main.SUB_PASTA + pastas.get((Integer) newValue));

                List<Time> times = new ArrayList<Time>();

                for(int i = 0; i < baseballElimination.getQtdTimes(); i++) {
                    Time time = new Time();
                    time.setNome(baseballElimination.getTimes()[i]);
                    time.setJogosGanhos(baseballElimination.getJogosGanhos()[i]);
                    time.setJogosPerdidos(baseballElimination.getJogosPerdidos()[i]);
                    time.setJogosRestantes(baseballElimination.getJogosRestantes()[i]);

                    String jogos = "";
                    for(int j = 0; j < baseballElimination.getJogosContra()[i].length; j++) {
                        jogos += baseballElimination.getJogosContra()[i][j] + "\t";
                    }
                    time.setJogos(jogos);

                    times.add(time);
                }

                tabela.setItems(FXCollections.observableArrayList(times));
            }
        });
    }

    public ChoiceBox getSelecao() {
        return selecao;
    }

    public void setSelecao(ChoiceBox selecao) {
        this.selecao = selecao;
    }

    public TableView getTabela() {
        return tabela;
    }

    public void setTabela(TableView tabela) {
        this.tabela = tabela;
    }

}
