package fr.talentRate.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import fr.talentRate.dto.plan.Promotion;

/**
 * Manage promotions.
 * @author djer13
 */
//@RepositoryRestResource(collectionResourceRel = "promotion", path = "promotion")
public interface PromotionRepository extends PagingAndSortingRepository<Promotion, Long> {

}
