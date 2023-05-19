package com.milkstgo.milkstgo.services;

import com.milkstgo.milkstgo.entities.SubirAcopioEntity;
import com.milkstgo.milkstgo.repositories.SubirAcopioRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class SubirAcopioService {

    @Autowired
    private SubirAcopioRepository subirAcopioRepository;

    private final Logger logg = LoggerFactory.getLogger(SubirAcopioService.class);

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerArchivo(String direccion){
        String texto = "";
        BufferedReader bf = null;
        subirAcopioRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    crearAcopio(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], Integer.parseInt(bfRead.split(";")[3]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public SubirAcopioEntity crearAcopio(String fechaStr, String turno, String proveedor, Integer Kgleche){

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha = LocalDate.parse(fechaStr, formato);

        SubirAcopioEntity nuevoAcopio = new SubirAcopioEntity();
        nuevoAcopio.setFecha(fecha);
        nuevoAcopio.setTurno(turno);
        nuevoAcopio.setProveedor(proveedor);
        nuevoAcopio.setKgleche(Kgleche);
        return subirAcopioRepository.save(nuevoAcopio);
    }

    public ArrayList<SubirAcopioEntity> verDatos(){
        return subirAcopioRepository.findAll();
    }

    public ArrayList<SubirAcopioEntity> findByProveedor(String proveedor) {
        return subirAcopioRepository.findByProveedor(proveedor);}

    public void eliminarData(){
        subirAcopioRepository.deleteAll();
    }
}
