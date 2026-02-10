package com.example.automarket.specification;

import com.example.automarket.model.Vehicle;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {

    public static Specification<Vehicle> hasBrand(String brand) {
        return (root, query, cb) ->
                brand == null ? null : cb.equal(root.get("brand"), brand);
    }

    public static Specification<Vehicle> hasModel(String model) {
        return (root, query, cb) ->
                model == null ? null : cb.like(cb.lower(root.get("model")),
                        "%" + model.toLowerCase() + "%");
    }

    public static Specification<Vehicle> priceBetween(
            Integer min, Integer max) {
        return getVehicleSpecification(min, max);
    }

    @NonNull
    private static Specification<Vehicle> getVehicleSpecification(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("manufactureYear"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("manufactureYear"), min);
            return cb.between(root.get("manufactureYear"), min, max);
    };
    }

    public static Specification<Vehicle> yearBetween(
            Integer min, Integer max) {
        return getVehicleSpecification(min, max);
    }

    public static Specification<Vehicle> fuelType(String fuelType) {
        return (root, query, cb) ->
                fuelType == null ? null : cb.equal(root.get("fuelType"), fuelType);
    }

    public static Specification<Vehicle> transmission(String transmission) {
        return (root, query, cb) ->
                transmission == null ? null : cb.equal(root.get("transmissionType"), transmission);
    }
}
