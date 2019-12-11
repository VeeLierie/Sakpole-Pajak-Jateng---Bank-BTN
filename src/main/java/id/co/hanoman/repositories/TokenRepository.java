package id.co.hanoman.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import id.co.hanoman.domain.Token;

@RepositoryRestResource
public interface TokenRepository extends CrudRepository<Token, Integer>{
	Token findByType(String type);
}
