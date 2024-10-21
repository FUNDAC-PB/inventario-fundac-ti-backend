package io.fundacti.inventario.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Entradas")
@Table(name = "entradas")
public class Entrada extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entradaid")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventarioid")
    @JsonbTransient
    private Inventario inventario;

    @Column(name = "tipoEntrada")
    private String tipoEntrada;
	
    @Column(name = "dataEntrada", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate dataEntrada;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "termoRecebimento")
    private String termoRecebimento;


    // Constructors
    public Entrada() {
    }

    public Entrada(Long id) {
        this.id = id;
    }

    public Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inventario getInventario() {
        return inventario;
    }
    
    // Referencia bidirecional
    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
        if (inventario != null && !inventario.getEntradas().contains(this)) {
            inventario.addEntrada(this);
        }
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getTermoRecebimento() {
        return termoRecebimento;
    }

    public void setTermoRecebimento(String termoRecebimento) {
        this.termoRecebimento = termoRecebimento;
    }


}