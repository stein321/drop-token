package b.stein.droptoken;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
    public Game findGameById(String id);


}
