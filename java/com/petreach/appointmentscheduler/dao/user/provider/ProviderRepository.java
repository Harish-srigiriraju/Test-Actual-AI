package com.petreach.appointmentscheduler.dao.user.provider;

import org.springframework.data.jpa.repository.Query;

import com.petreach.appointmentscheduler.dao.user.CommonUserRepository;
import com.petreach.appointmentscheduler.entity.Work;
import com.petreach.appointmentscheduler.entity.user.provider.Provider;

import java.util.List;

public interface ProviderRepository extends CommonUserRepository<Provider> {

    List<Provider> findByWorks(Work work);

    @Query("select distinct p from Provider p inner join p.works w where w.targetCustomer = 'retail'")
    List<Provider> findAllWithRetailWorks();

    @Query("select distinct p from Provider p inner join p.works w where w.targetCustomer = 'corporate'")
    List<Provider> findAllWithCorporateWorks();
}
