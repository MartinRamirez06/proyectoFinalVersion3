package org.example.Controlador;

import org.example.Vista.VentanaAnimal;
import org.example.modelo.Animal;
import org.example.modelo.ModeloTablaAnimal;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;

public class ControladorAnimal extends MouseAdapter {
    private VentanaAnimal view;
    private ModeloTablaAnimal modelo;


    public ControladorAnimal(VentanaAnimal view) {
        this.view = view;
        modelo=new ModeloTablaAnimal();
        this.view.getTblAnimal().setModel(modelo);
        this.view.getTblAnimal().addMouseListener(this);
        this.view.getBtnCargar().addMouseListener(this);
        this.view.getBtnAgregar().addMouseListener(this);
        this.view.getBtnEliminar().addMouseListener(this);
        this.view.getBtnActualizar().addMouseListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==this.view.getBtnCargar()){
            modelo.cargarDatos();
            this.view.getTblAnimal().setModel(modelo);
            this.view.getTblAnimal().updateUI();
        }

        if (e.getSource()==this.view.getBtnAgregar()){
            Animal animal=new Animal();
            animal.setId(0);
            animal.setNombre(this.view.getTxtNombre().getText());
            animal.setNombreCientifico(this.view.getTxtNombreCientifico().getText());
            animal.setTipoDeDieta(this.view.getTxtTipoDeDieta().getText());
            animal.setTamaDeEspecie(this.view.getTxtTamaDeEspecie().getText());
            animal.setLinkImagen(this.view.getTxtLinkImagen().getText());
            if(modelo.agregarAnimal(animal)){
                JOptionPane.showMessageDialog(view,"Se agrego correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
                this.view.getTblAnimal().updateUI();
            }else {
                JOptionPane.showMessageDialog(view,"No se pudo agregar a la base de datos.Por favor revise su conexion","Error al insertar",JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource()==this.view.getBtnEliminar()){
            int selectedRow = this.view.getTblAnimal().getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(view, "¿Seguro que quieres eliminar este animal?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    modelo.eliminarAnimal(selectedRow);
                    this.view.getTblAnimal().updateUI();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Selecciona un animal para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (e.getSource()==this.view.getBtnActualizar()){
                //modelo.cargarDatos();
                //this.view.getTblAnimal().updateUI();
                modelo.cargarDatos();
                this.view.getTblAnimal().setModel(modelo);
                this.view.getTblAnimal().updateUI();
            }
        }
        if (e.getSource()==view.getTblAnimal()){
            System.out.println("Evento sobre la tabla");
            int index=this.view.getTblAnimal().getSelectedRow();
            Animal temp=modelo.getAnimalIndex(index);
            try {
                this.view.getLblImagen().setIcon(temp.getImagen());
                this.view.getLblImagen().setText("");
            }catch (MalformedURLException murle){
                System.out.println(e.toString());
            }

        }
        this.view.limpiar();
    }

}
