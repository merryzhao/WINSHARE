package com.winxuan.ec.model.manufacturer;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 制造商
 * @author heyadong
 * @version 1.0, 2012-9-17 上午10:24:51
 */
@Entity
@Table(name="manufacturer")
public class Manufacturer implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 9154563436947056535L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column
    private String name;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="manufacturer", targetEntity=ManufacturerItem.class)
    private List<ManufacturerItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ManufacturerItem> getItems() {
        return items;
    }

    public void setItems(List<ManufacturerItem> items) {
        this.items = items;
    }
    @Override 
    public String toString(){
        return String.format("[id:%s] ",id);
    }
}
