
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
@Table(name = "tipo_documento")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "TipoDocumento.findAll", query = "SELECT t FROM TipoDocumento t"),
        @NamedQuery(name = "TipoDocumento.findByTipoDocumentoId", query = "SELECT t FROM TipoDocumento t WHERE t.tipoDocumentoId = :tipoDocumentoId"),
        @NamedQuery(name = "TipoDocumento.findByCodigo", query = "SELECT t FROM TipoDocumento t WHERE t.codigo = :codigo"),
        @NamedQuery(name = "TipoDocumento.findByDescripcion", query = "SELECT t FROM TipoDocumento t WHERE t.descripcion = :descripcion") })
public class TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipo_documento_id")
    private Integer tipoDocumentoId;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumentoId")
    private List<Cliente> clienteList;

    public TipoDocumento() {
    }

    public TipoDocumento(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public TipoDocumento(Integer tipoDocumentoId, String codigo, String descripcion) {
        this.tipoDocumentoId = tipoDocumentoId;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoDocumentoId != null ? tipoDocumentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDocumento)) {
            return false;
        }
        TipoDocumento other = (TipoDocumento) object;
        if ((this.tipoDocumentoId == null && other.tipoDocumentoId != null)
                || (this.tipoDocumentoId != null && !this.tipoDocumentoId.equals(other.tipoDocumentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "creadordeentidades.TipoDocumento[ tipoDocumentoId=" + tipoDocumentoId + " ]";
    }

}
