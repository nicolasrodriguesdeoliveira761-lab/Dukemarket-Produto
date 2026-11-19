
package br.dev.nicolas.dukemarket.graphic.model;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.logging.Level;
    import java.util.logging.Logger;
/**
 *
 * @author sesi2dib
 */
public class Clientes {
   private int id;
    private String Endereco;
    private String NomeCompleto;
    private String CPF;
    private String Cidade;
    private String UF;
    private Calendar dataCadastro;

    
    // --- COnstrutores ---

    public Clientes() {
    }

    public Clientes(int id, String Endereco, String NomeCompleto, String CPF, String Cidade, String UF, Calendar dataCadastro) {
        this.id = id;
        this.Endereco = Endereco;
        this.NomeCompleto = NomeCompleto;
        this.CPF = CPF;
        this.Cidade = Cidade;
        this.UF = UF;
        this.dataCadastro = dataCadastro;
    }
    
      

    // --- GETTERS E SETTERS PADRÕES ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String Endereco) {
        this.Endereco = Endereco;
    }

    public String getNomeCompleto() {
        return NomeCompleto;
    }

    public void setNomeCompleto(String NomeCompleto) {
        this.NomeCompleto = NomeCompleto;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

 
    public void setDataCadastro(String strDataCadastro) {
        try {
            this.dataCadastro = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.dataCadastro.setTime(sdf.parse(strDataCadastro));
        } catch (ParseException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDataCadastro() {
        if (this.dataCadastro != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.format(this.dataCadastro.getTime());
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return String.format("%3d | %15s | %-30s | %10.2f | %10.2f | %10.2s | %10s", 
                this.id, 
                this.Endereco, 
                this.NomeCompleto, 
                this.CPF, 
                this.Cidade, 
                this.UF, 
                this.getDataCadastro());
    }

   
}
