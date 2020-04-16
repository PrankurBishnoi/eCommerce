package com.prankur.eCommerce.repositories;

import com.prankur.eCommerce.models.User;
import com.prankur.eCommerce.models.VerificationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TokenRepository extends CrudRepository<VerificationToken,Long>
{
    Optional<VerificationToken> findByTokenAndIsdeleted(String token, Boolean isdeleted);
//    Optional<VerificationToken> findByTokenAndIsdeleted(String token,int isdeleted);
    Optional<VerificationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update VerificationToken v set v.isdeleted = true where v.user =:user")
    void deleteAllExistingTokenForGivenUser(User user);



}
