package com.amisha.rate_limiter.service;

import com.amisha.rate_limiter.entity.Tenant;
import com.amisha.rate_limiter.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TenantService {

    private final TenantRepository repository;

    public TenantService(TenantRepository repository) {
        this.repository = repository;
    }

    public Tenant createTenant(Tenant tenant) {
        Optional<Tenant> existingTenant = repository.findByTenantKey(tenant.getTenantKey());
        if (existingTenant.isPresent()) {
            throw new RuntimeException("Tenant with this key already exists");
        }
        return repository.save(tenant);

    }

    public List<Tenant> getAllTennants() {
        return repository.findAll();
    }

    public Tenant getTenantByKey(String tenantkey) {
        return repository.findByTenantKey(tenantkey).orElseThrow(() -> new RuntimeException("Tenant not found"));

    }

}
