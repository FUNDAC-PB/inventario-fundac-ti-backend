package io.fundacti.inventario.domain.model;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "Inventario")
@Table(name = "inventario")
public class Inventario extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventarioid")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotacaoid")
    @JsonbTransient
    private Lotacao lotacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setorid")
    @JsonbTransient
    private Setor setor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoriaid")
    @JsonbTransient
    private Categoria categoria;

    @OneToOne(mappedBy = "inventario", cascade = CascadeType.ALL,
              fetch = FetchType.LAZY, orphanRemoval=true)
    @JsonbTransient
    private Garantia garantia;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL,
              fetch = FetchType.LAZY, orphanRemoval=true)
    @JsonbTransient
    private List<Entrada> entradas = new ArrayList<>();

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL,
              fetch = FetchType.LAZY, orphanRemoval=true)
    @JsonbTransient
    private List<Saida> saidas = new ArrayList<>();

    @Column(name = "patrimonio")
    private String patrimonio;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "status")
    private String status;

    // Getters e Setters

    // Constructors
    public Inventario() {
    }

    public Inventario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Lotacao getLotacao() {
        return lotacao;
    }

    public void setLotacao(Lotacao lotacao) {
        this.lotacao = lotacao;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Garantia getGarantia() {
        return garantia;
    }

    // Referencias bidirecionais
    public void setGarantia(Garantia garantia) {
        this.garantia = garantia;
        if (garantia != null) {
            garantia.setInventario(this);
        }
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntrada(List<Entrada> entradas) {
        this.entradas = entradas;
        for (Entrada entrada : entradas) {
            entrada.setInventario(this);
        }
    }

    // Entrada unica
    public void addEntrada(Entrada entrada) {
        entradas.add(entrada);
        entrada.setInventario(this);
    }

    public List<Saida> getSaidas() {
        return saidas;
    }

    public void setSaida(List<Saida> saidas) {
        this.saidas = saidas;
        for(Saida saida : saidas) {
           saida.setInventario(this);
        }
    }

    // Saida unica
    public void addSaida(Saida saida) {
        saidas.add(saida);
        saida.setInventario(this);
    }

}