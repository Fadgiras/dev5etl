package com.fadgiras.dev5etl.controller;

import com.fadgiras.dev5etl.dto.FabProdsDateDTO;
import com.fadgiras.dev5etl.dto.MagasinProductDTO;
import com.fadgiras.dev5etl.dto.ManufacturerProductDTO;
import com.fadgiras.dev5etl.model.PointVenteEntity;
import com.fadgiras.dev5etl.repository.PointVenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/add/bulk", method = RequestMethod.GET)
    public List<PointVenteEntity> addPointVentes(List<PointVenteEntity> pointVenteEntities) {
        for (PointVenteEntity pointVenteEntity : pointVenteEntities) {
            pointVenteRepository.save(pointVenteEntity);
        }
        return pointVenteRepository.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public PointVenteEntity findPointVente(@PathVariable(value = "id") int id) {
        return pointVenteRepository.findById(id).get();
    }

    @RequestMapping(value = "/find/cat/{id}", method = RequestMethod.GET)
    public List<PointVenteEntity> findPointVenteByCategorie(@PathVariable(value = "id") int id) {
        return pointVenteRepository.findByCatid(id);
    }

    @RequestMapping(value = "/find/mag/{id}", method = RequestMethod.GET)
    public List<PointVenteEntity> findPointVenteByMagasin(@PathVariable(value = "id") int id) {
        return pointVenteRepository.findByMagid(id);
    }

    @RequestMapping(value = "/find/fab/{id}", method = RequestMethod.GET)
    public List<PointVenteEntity> findPointVenteByFabricant(@PathVariable(value = "id") int id) {
        return pointVenteRepository.findByFabid(id);
    }

    @RequestMapping(value = "/count/actors/cat/{id}", method = RequestMethod.GET)
    public long countActors(@PathVariable(value = "id") int id) {
        return pointVenteRepository.countDistinctActorsForCategory(id);
    }

    @RequestMapping(value = "/count/prod/fab/cat/{id}", method = RequestMethod.GET)
    public List<ManufacturerProductDTO> countProduitByCategorie(@PathVariable(value = "id") int id) {
        return pointVenteRepository.countProduitByCategorieManufacturer(id);
    }

//    @RequestMapping(value = "/count/prod/cat/{id}", method = RequestMethod.GET)
//    public Integer countProduitByCategorieMagasin(@PathVariable(value = "id") int id) {
//        return pointVenteRepository.countProduitByCategorieManufacturer(id);
//    }

    //1.3
    @RequestMapping(value = "/top/3months", method = RequestMethod.GET)
    public List<MagasinProductDTO> top() {
        Date lastDate =new Date(pointVenteRepository.getLastDateEntry().getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(lastDate);
        c.add(Calendar.MONTH, -3);
        System.err.println(lastDate);
        System.err.println(c.getTime());
        return pointVenteRepository.findTopMagasinsByProducts(c.getTime(), lastDate).stream().limit(10).collect(Collectors.toList());
    }

    @RequestMapping(value = "/top/{date1}/{date2}", method = RequestMethod.GET)
    public List<MagasinProductDTO> topDate(@PathVariable(value = "date1") String date1, @PathVariable(value = "date2") String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date lastDate = sdf.parse(date2);
        Date firstDate = sdf.parse(date1);
        return pointVenteRepository.findTopMagasinsByProducts(firstDate, lastDate).stream().limit(10).collect(Collectors.toList());
    }

//    @RequestMapping(value = "/top/fab/{fabid}/cat/{catid}", method = RequestMethod.GET)
//    public List<Integer> topByFabAndCat(@PathVariable(value = "fabid") int fabid, @PathVariable(value = "catid") int catid) {
//        List<Integer> list =  pointVenteRepository.findTop10MagasinIdsForCategory(catid).stream().limit(10).collect(Collectors.toList());
//        System.err.println(list);
//        System.err.println(pointVenteRepository.productsInTopMagasins(catid, list));
//        return pointVenteRepository.productsFromManufacturerInTopMagasins(fabid, catid, list);
//    }

    //1.2
    @RequestMapping(value = "/avg/produit/cat/{id}", method = RequestMethod.GET)
    public Long avgProdByCategorie(@PathVariable(value = "id") int id) {
        List list = pointVenteRepository.countProduitByCategorieManufacturer(id);
        if (list.size() == 0) return null;
        Long sum = 0L;
        for (Object dto : list) {
            ManufacturerProductDTO manufacturerProductDTO = (ManufacturerProductDTO) dto;
            sum += manufacturerProductDTO.getProductCount();
        }
        return sum / list.size();
    }

    Map<String,Map<String,Long>> aggregateDto(List<FabProdsDateDTO> list){
        Map<String, Map<String, Long>> map = new HashMap<>();
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("01-MM-yyyy", Locale.ENGLISH);
//        System.err.println(list);
        for (FabProdsDateDTO dto : list) {
//            System.err.println("\n\n"+ dto);
//            System.err.println(sdfMonth.format(dto.getDate()));
//            System.err.println(sdfYear.format(dto.getDate()));
//            System.err.println(map.containsKey(sdfYear.format(dto.getDate())));
//            System.err.println(map.keySet());
//            System.err.println(map.get(sdfYear.format(dto.getDate())));


            if (map.containsKey(sdfYear.format(dto.getDate()))){
                Map<String, Long> map1 = map.get(sdfYear.format(dto.getDate()));
//                System.err.println("map1");
//                System.err.println(map1);
                if (map1.containsKey(sdfMonth.format(dto.getDate()))){
//                    System.err.println("contains");
//                    System.err.println(map1);
//                    System.err.println(dto.getProd());
                    map1.put(
                            sdfMonth.format(dto.getDate()),
                            map.get(
                                    sdfYear.format(dto.getDate())
                            ).get(sdfMonth.format(dto.getDate())) + (dto.getProd()!=null?dto.getProd():0)
                    );
//                    System.err.println("after");
//                    System.err.println(map1);
                    map.put(sdfYear.format(dto.getDate()), map1);
                }else {
//                    System.err.println("!contains month");
                    map1.put(sdfMonth.format(dto.getDate()), dto.getProd());
//                    System.err.println(map1);
                    map.put(sdfYear.format(dto.getDate()), map1);
//                    System.err.println("after Month add");
//                    System.err.println(map);
                }
            }
            else {
//                System.err.println("!contains");
                Map<String, Long> map1 = new HashMap<>();
                map1.put(sdfMonth.format(dto.getDate()), dto.getProd());
                map.put(sdfYear.format(dto.getDate()), map1);
            }
        }
//        System.err.println(map);
        return map;
    }

    @RequestMapping(value = "/health/fab/{fabid}/cat/{catid}/date/{date1}/{date2}", method = RequestMethod.GET)
    public ArrayList checkFabricantHealth(@PathVariable int fabid, @PathVariable int catid, @PathVariable String date1, @PathVariable String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(date1);
        Date endDate = sdf.parse(date2);
        List<Integer>  listprod ;
        List<Integer> listid = new ArrayList<>();
        // histo : part générale  ->  sum de tout les prod du fab sur le mois /  Sum de tout les prod sur le mois
        // top : pour chaque mag du top avoir le nombre de prod du fab / nombre de prod total du mag le tout en %
        listid = pointVenteRepository.findTop10MagasinIdForCategory(catid, startDate, endDate).stream().limit(10).collect(Collectors.toCollection(ArrayList::new));
        List<?> list = pointVenteRepository.findTop10MagasinMarketShareForCategory(catid, startDate, endDate).stream().limit(10).collect(Collectors.toList());

//        System.err.println(pointVenteRepository.countProductsInCategoryMonth(catid));
        List<FabProdsDateDTO> dtos = pointVenteRepository.countProductsInCategoryMonth(catid);
        dtos.sort(Comparator.comparing(FabProdsDateDTO::getDate).reversed());
//        System.err.println(dtos);

//        System.err.println(aggregateDto(pointVenteRepository.countProductsInCategoryMonth(catid)));
//        System.err.println(pointVenteRepository.countProductsInCategoryMonthByFab(catid, fabid));
//        System.err.println("Find top 10 market share");
//        System.err.println(list);
//        System.err.println("Find top 10 magasin id");
//        System.err.println(pointVenteRepository.findTop10MagasinIdForCategory(catid, startDate, endDate));
//        System.err.println("Find top 10 magasin id limit 10");
//        System.err.println(listid);
//        System.err.println("get number of prod in each magasin of top 10");
//        System.err.println(pointVenteRepository.findTop10MagasinProdForCategory(catid, startDate, endDate).stream().limit(10).collect(Collectors.toList()));
//
//        System.err.println(pointVenteRepository.findFabricantHealth(fabid, catid, startDate, endDate));
//        System.err.println(pointVenteRepository.findFabricantHealth(fabid, catid, startDate, endDate).size());
//
//        System.err.println(pointVenteRepository.getProductsCountByMagasinForCategoryAndFabricant(catid, fabid, startDate, endDate, listid));
//        System.err.println(pointVenteRepository.getProductsCountByMagasinForCategoryAndFabricant(catid, fabid, startDate, endDate, listid).size());
//
//        System.err.println(pointVenteRepository.countProductsInCategoryMonth(catid, startDate, endDate));
//
//        ArrayList<Integer> orderList = pointVenteRepository.findTop10MagasinIdForCategory(catid, startDate, endDate);
//        List<FabricantHealthDTO> orderedList =pointVenteRepository.findFabricantHealth(fabid, catid, startDate, endDate);
//        orderedList.sort(Comparator.comparingInt(dto -> orderList.indexOf(dto.getMagid())));
//        System.err.println(orderedList);

          ArrayList result = new ArrayList();
            result.add(aggregateDto(pointVenteRepository.countProductsInCategoryMonthByFab(catid, fabid)));
            result.add(aggregateDto(pointVenteRepository.countProductsInCategoryMonth(catid)));
            result.add(pointVenteRepository.getProductsCountByMagasinForCategory(catid, startDate, endDate, listid));
            result.add(pointVenteRepository.getProductsCountByMagasinForCategoryAndFabricant(catid, fabid, startDate, endDate, listid));

        return result;
    }


}
