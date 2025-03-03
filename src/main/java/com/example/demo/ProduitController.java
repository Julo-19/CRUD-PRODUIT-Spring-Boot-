package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produit")



public class ProduitController {

        @Autowired
     private ProduitRepository produitRepository;


    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return new ResponseEntity<>(produitRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Produit> addProduit(@RequestBody Produit produit) {
        Produit savedProduit = produitRepository.save(produit);
        return new ResponseEntity<>(savedProduit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Optional<Produit> produit = produitRepository.findById(id);

        return produit.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produitDetails) {
        Optional<Produit> produit = produitRepository.findById(id);

        if (produit.isPresent()) {
            Produit existingProduit = produit.get();
            existingProduit.setNom(produitDetails.getNom());
            existingProduit.setPrix(produitDetails.getPrix());
            existingProduit.setDescription(produitDetails.getDescription());
            Produit produitUpdate =  produitRepository.save(existingProduit);
            return new ResponseEntity<>(produitUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        Optional<Produit> produit = produitRepository.findById(id);

        if (produit.isPresent()) {
            produitRepository.delete(produit.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
