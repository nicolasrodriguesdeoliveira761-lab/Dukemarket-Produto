package br.dev.nicolas.dukemarket.graphic.controller;

import br.dev.nicolas.dukemarket.graphic.App;
import br.dev.nicolas.dukemarket.graphic.DAO.ProdutosDAO;
import br.dev.nicolas.dukemarket.graphic.model.Produto;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sesi2dib
 */
public class ScrClientesController implements Initializable {

    @FXML
    TableView <Produto> tblClientes;

    @FXML
    TableColumn <Produto, Integer> tcoId;

    @FXML
    TableColumn <Produto, String> tcoEndereco;

    @FXML
    TableColumn <Produto, String> tcoNomeCompleto;

    @FXML
    TableColumn <Produto, Double> tcoCPF;

    @FXML
    TableColumn <Produto, Double> tcoCidade;

    @FXML
    TableColumn <Produto, Double> tcoUF;

    @FXML
    TableColumn <Produto, Calendar> tcoDataCadastro;

    @FXML
    TextField txtId;

    @FXML
    TextField txtEndereco;

    @FXML
    TextField txtNomeCompleto;

    @FXML
    TextField txtCPF;

    @FXML
    TextField txtCidade;

    @FXML
    TextField txtUF;

    @FXML
    DatePicker dtpDataCadastro;

    ProdutosDAO pDAO;
    Produto pClicked;    // Armazena o produto clicado na tabela
    private boolean flagNovo;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindColumns();
        
        carregaDados();
    }    
    /**
 * Conecta as colunas da TableView com os atributos da classe Produto.
 * @return void
 */
private void bindColumns() {

    // Vincula a coluna tcoId com o atributo id da classe Produto
    tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));

    // Adiciona uma configuração extra de alinhamento à direita.
    tcoId.setStyle("-fx-alignment: CENTER_RIGHT;");

    tcoId.setCellValueFactory(new PropertyValueFactory<>("codBarras"));
    tcoId.setStyle("-fx-alignment: CENTER_RIGHT;");

    tcoNomeCompleto.setCellValueFactory(new PropertyValueFactory<>("descricao"));

    tcoCPF.setCellValueFactory(new PropertyValueFactory<>("qtd"));
    tcoCPF.setStyle("-fx-alignment: CENTER_RIGHT;");

    tcoCidade.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
    tcoCidade.setStyle("-fx-alignment: CENTER_RIGHT;");

    tcoUF.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
    tcoUF.setStyle("-fx-alignment: CENTER_RIGHT;");

    tcoDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
    tcoDataCadastro.setStyle("-fx-alignment: CENTER_RIGHT;");

}

/**
 *
 * Carrega os dados de Produto que estão na base e vincula a TableView.
 *
 */
public void carregaDados(){

    this.pDAO = new ProdutosDAO();

    // Carrega view tblProduto com todos os resultados de produto.
    this.tblClientes.getItems().setAll(pDAO.getResults());

}

/**
 * Action do botão btnNovo.
 * Limpa os campos e posiciona o focus em txtId.
 *
 * @return void
 */
@FXML
private void btnNovoClick() {
    txtId.setText("auto");
    txtId.setEditable(false);

    txtId.setText("");
    txtId.requestFocus();

    txtNomeCompleto.setText("");
    txtCPF.setText("");
    txtCidade.setText("");
    txtUF.setText("");
    dtpDataCadastro.setValue(null);
    
    this.flagNovo = true;
} 
/**
     * Gerencia o click na tabela, identificando qual registro foi selecionado e
     * preenchendo os TextFields.
     */
    @FXML
    public void tblProdutoOnMouseClicked() {

        // getSelectedItem devolve um objeto Produto.
        // pClicked representa o Produto Clicado
        this.pClicked = tblClientes.getSelectionModel().getSelectedItem();

        if (pClicked != null) {

            txtId.setText(String.valueOf(pClicked.getId()));
            txtId.setEditable(false);

            txtId.setText(pClicked.getCodBarras());
            txtNomeCompleto.setText(pClicked.getDescricao());

            txtCPF.setText(String.valueOf(pClicked.getQtd()));
            txtCidade.setText(String.valueOf(pClicked.getValorCompra()));
            txtUF.setText(String.valueOf(pClicked.getValorVenda()));

            // Tratamento da data do cadastro:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            formatter = formatter.withLocale(Locale.US);
            
            LocalDate date = LocalDate.parse(pClicked.getDataCadastro(), formatter);

            dtpDataCadastro.setValue(date);
            dtpDataCadastro.setEditable(false);

            this.flagNovo = false;
        }

    }
    /**
     * Gerencia o click do botão Salvar. Adiciona um novo registro quando
     * flagNovo == true ou atualiza quando flagNovo == false;
     * 
     */
    @FXML
    public void btnSalvarClick() {

        Produto p = new Produto();

        p.setCodBarras(txtId.getText());
        p.setDescricao(txtNomeCompleto.getText());
        p.setQtd(Double.parseDouble(txtCPF.getText()));
        p.setValorCompra(Double.parseDouble(txtCidade.getText()));
        p.setValorVenda(Double.parseDouble(txtUF.getText()));

        if (this.flagNovo) {
            // Novo produto...

            this.pDAO.create(p);

        } else {
            // Ops.. é uma atualização!
            p.setId(Integer.parseInt(txtId.getText()));
            this.pDAO.update(p);

        }

        this.carregaDados();
    }
    /**
     * Gerencia o click do botão Excluir. Remove o registro selecionado -
     * carregado em pClicked.
     * 
     */
    @FXML
    public void btnExcluirClick() {

        if (this.pClicked != null) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirma Exclusão de Produto?");
            alert.setHeaderText(pClicked.getDescricao());
            alert.setContentText("Tem certeza que deseja excluir ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.pDAO.delete(this.pClicked.getId());

                this.tblClientes.getItems().remove(this.pClicked);

            }
            this.tblClientes.getSelectionModel().clearSelection();
            pClicked = null;

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ops!");
            alert.setHeaderText("Atenção");
            alert.setContentText("Você deve selecionar um registro antes de excluí-lo!");

            alert.showAndWait();

        }
    }
    public void btnProdutosClick() throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("scrProdutos.fxml"));
            /*
             * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 900, 400);
            Stage stage = new Stage();

            stage.setTitle("CRUD Produtos");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window scrProdutos.", e);

        }

    }
}