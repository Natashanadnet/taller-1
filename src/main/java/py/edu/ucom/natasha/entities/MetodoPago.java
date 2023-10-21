
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
@Table(name = "metodo_pago")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "MetodoPago.findAll", query = "SELECT m FROM MetodoPago m"),
        @NamedQuery(name = "MetodoPago.findByMetodoPagoId", query = "SELECT m FROM MetodoPago m WHERE m.metodoPagoId = :metodoPagoId"),
        @NamedQuery(name = "MetodoPago.findByCodigo", query = "SELECT m FROM MetodoPago m WHERE m.codigo = :codigo"),
        @NamedQuery(name = "MetodoPago.findByDescripcion", query = "SELECT m FROM MetodoPago m WHERE m.descripcion = :descripcion") })
public class MetodoPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "metodo_pago_id")
    private Integer metodoPagoId;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metodoPagoId")
    private List<Venta> ventaList;

    public MetodoPago() {
    }

    public MetodoPago(Integer metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }

    public MetodoPago(Integer metodoPagoId, String codigo, String descripcion) {
        this.metodoPagoId = metodoPagoId;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(Integer metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
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
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (metodoPagoId != null ? metodoPagoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetodoPago)) {
            return false;
        }
        MetodoPago other = (MetodoPago) object;
        if ((this.metodoPagoId == null && other.metodoPagoId != null)
                || (this.metodoPagoId != null && !this.metodoPagoId.equals(other.metodoPagoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "creadordeentidades.MetodoPago[ metodoPagoId=" + metodoPagoId + " ]";
    }

}
