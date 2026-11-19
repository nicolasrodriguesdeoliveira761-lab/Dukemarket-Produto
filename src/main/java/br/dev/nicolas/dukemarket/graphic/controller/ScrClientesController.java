package br.dev.nicolas.dukemarket.graphic.controller;

import br.dev.nicolas.dukemarket.graphic.App;
import br.dev.nicolas.dukemarket.graphic.DAO.ClientesDAO;
import br.dev.nicolas.dukemarket.graphic.model.Clientes;
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
    TableView<Clientes> tblClientes;
    
    @FXML
    TableColumn<Clientes, Integer> tcoId;
    
    @FXML
    TableColumn<Clientes, String> tcoEndereco;
    
    @FXML
    TableColumn<Clientes, String> tcoNomeCompleto;
    
    @FXML
    TableColumn<Clientes, String> tcoCPF;
    
    @FXML
    TableColumn<Clientes, String> tcoCidade;
    
    @FXML
    TableColumn<Clientes, String> tcoUF;
    
    @FXML
    TableColumn<Clientes, Calendar> tcoDataCadastro;
    
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
    
    ClientesDAO cDAO;
    Clientes cClicked;    // Armazena o produto clicado na tabela
    private boolean flagNovo;

    /**
     * Initializes the controller class.
     *
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
     *
     * @return void
     */
    private void bindColumns() {

        // Vincula a coluna tcoId com o atributo id da classe Produto
        tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Adiciona uma configuração extra de alinhamento à direita.
        tcoId.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
        tcoEndereco.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoNomeCompleto.setCellValueFactory(new PropertyValueFactory<>("NomeCompleto"));
        
        tcoCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        tcoCPF.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoCidade.setCellValueFactory(new PropertyValueFactory<>("Cidade"));
        tcoCidade.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoUF.setCellValueFactory(new PropertyValueFactory<>("UF"));
        tcoUF.setStyle("-fx-alignment: CENTER_RIGHT;");
        
        tcoDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        tcoDataCadastro.setStyle("-fx-alignment: CENTER_RIGHT;");
        
    }

    /**
     *
     * Carrega os dados de Produto que estão na base e vincula a TableView.
     *
     */
    public void carregaDados() {
        
        this.cDAO = new ClientesDAO();

        // Carrega view tblProduto com todos os resultados de produto.
        this.tblClientes.getItems().setAll(cDAO.getResults());
        
    }

    /**
     * Action do botão btnNovo. Limpa os campos e posiciona o focus em txtId.
     *
     * @return void
     */
    @FXML
    private void btnNovoClick() {
        txtId.setText("auto");
        txtId.setEditable(false);
        
        txtId.setText("");
        txtId.requestFocus();
        
        txtEndereco.setText("");
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
    public void tblClientesOnMouseClicked() {

        // getSelectedItem devolve um objeto Produto.
        // pClicked representa o Produto Clicado
        this.cClicked = tblClientes.getSelectionModel().getSelectedItem();
        
        if (cClicked != null) {
            
            txtId.setText(String.valueOf(cClicked.getId()));
            txtId.setEditable(false);
            
            txtId.setText(cClicked.getEndereco());
            txtNomeCompleto.setText(cClicked.getNomeCompleto());
            
            txtEndereco.setText(cClicked.getEndereco());
            
            txtCPF.setText(String.valueOf(cClicked.getCPF()));
            txtCidade.setText(String.valueOf(cClicked.getCidade()));
            txtUF.setText(String.valueOf(cClicked.getUF()));

            // Tratamento da data do cadastro:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            formatter = formatter.withLocale(Locale.US);
            
            LocalDate date = LocalDate.parse(cClicked.getDataCadastro(), formatter);
            
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
        
        Clientes c = new Clientes();
        
        c.setEndereco(txtEndereco.getText());
        c.setNomeCompleto(txtNomeCompleto.getText());
        c.setCPF(txtCPF.getText());
        c.setCidade(txtCidade.getText());
        c.setUF(txtUF.getText());
        
        if (this.flagNovo) {
            // Novo produto...

            this.cDAO.create(c);
            
        } else {
            // Ops.. é uma atualização!
            c.setId(Integer.parseInt(txtId.getText()));
            this.cDAO.update(c);
            
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
        
        if (this.cClicked != null) {
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirma Exclusão de Cliente?");
            alert.setHeaderText(cClicked.getNomeCompleto());
            alert.setContentText("Tem certeza que deseja excluir ?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.cDAO.delete(this.cClicked.getId());
                
                this.tblClientes.getItems().remove(this.cClicked);
                
            }
            this.tblClientes.getSelectionModel().clearSelection();
            cClicked = null;
            
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ops!");
            alert.setHeaderText("Atenção");
            alert.setContentText("Você deve selecionar um registro antes de excluí-lo!");
            
            alert.showAndWait();
            
        }
    }

    public void btnClientesClick() throws IOException {
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("scrClientes.fxml"));
            /*
             * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 900, 400);
            Stage stage = new Stage();
            
            stage.setTitle("CRUD Clientes");
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window scrClientes.", e);
            
        }
        
    }
}
