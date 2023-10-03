package com.fadgiras.dev5etl.controller;

import com.fadgiras.dev5etl.model.PointVenteEntity;
import com.fadgiras.dev5etl.repository.PointVenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pointvente")
public class PointVenteController {

    @Autowired
    PointVenteRepository pointVenteRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<PointVenteEntity> getAllPointVentes() {
        return pointVenteRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PointVenteEntity addPointVente(PointVenteEntity pointVenteEntity) {
        return pointVenteRepository.save(pointVenteEntity);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deletePointVente(PointVenteEntity pointVenteEntity) {
        pointVenteRepository.delete(pointVenteEntity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public PointVenteEntity updatePointVente(PointVenteEntity pointVenteEntity) {
        return pointVenteRepository.save(pointVenteEntity);
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public PointVenteEntity findPointVente(PointVenteEntity pointVenteEntity) {
        return pointVenteRepository.findById(pointVenteEntity.getId()).get();
    }

    @RequestMapping(value = "/add/bulk", method = RequestMethod.GET)
    public List<PointVenteEntity> addPointVentes(List<PointVenteEntity> pointVenteEntities) {
        for (PointVenteEntity pointVenteEntity : pointVenteEntities) {
            pointVenteRepository.save(pointVenteEntity);
        }
        return pointVenteRepository.findAll();
    }


}
