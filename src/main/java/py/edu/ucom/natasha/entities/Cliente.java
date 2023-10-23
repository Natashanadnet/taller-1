
package py.edu.ucom.natasha.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

/**
 *
 * @author natas
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
        @NamedQuery(name = "Cliente.findByClienteId", query = "SELECT c FROM Cliente c WHERE c.clienteId = :clienteId"),
        @NamedQuery(name = "Cliente.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"),
        @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos"),
        @NamedQuery(name = "Cliente.findByDocumento", query = "SELECT c FROM Cliente c WHERE c.documento = :documento"),
        @NamedQuery(name = "Cliente.findByEsClienteFiel", query = "SELECT c FROM Cliente c WHERE c.esClienteFiel = :esClienteFiel") })
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cliente_id")
    private Integer clienteId;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "documento")
    private String documento;
    @Column(name = "es_cliente_fiel")
    private Boolean esClienteFiel;
    @JoinColumn(name = "tipo_documento_id", referencedColumnName = "tipo_documento_id")
    @ManyToOne(optional = false)
    private TipoDocumento tipoDocumentoId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId")
    private List<Venta> ventaList;

    public Cliente() {
    }

    public Cliente(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Cliente(Integer clienteId, String nombres, String apellidos, String documento) {
        this.clienteId = clienteId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Boolean getEsClienteFiel() {
        return esClienteFiel;
    }

    public void setEsClienteFiel(Boolean esClienteFiel) {
        this.esClienteFiel = esClienteFiel;
    }

    public TipoDocumento getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(TipoDocumento tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteId != null ? clienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clienteId == null && other.clienteId != null)
                || (this.clienteId != null && !this.clienteId.equals(other.clienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "creadordeentidades.Cliente[ clienteId=" + clienteId + " ]";
    }

}
