package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SellerRepos extends CrudRepository<Seller,Long>
{
        Boolean existsByGst(String gst);
        Boolean existsByCompanyNameIgnoreCase(String companyName);
        List<Seller> findByEmailLike(Pageable pageable, String email);
        Seller findByEmail(String email);
}
