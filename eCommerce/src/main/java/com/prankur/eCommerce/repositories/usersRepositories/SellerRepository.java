package com.prankur.eCommerce.repositories.usersRepositories;

import com.prankur.eCommerce.models.users.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends CrudRepository<Seller,Long>
{
        Boolean existsByGst(String gst);
        Boolean existsByCompanyNameIgnoreCase(String companyName);
        List<Seller> findByEmailLike(Pageable pageable, String email);
        Seller findByEmail(String email);
}
