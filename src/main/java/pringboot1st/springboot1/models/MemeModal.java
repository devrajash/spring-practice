package pringboot1st.springboot1.models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import pringboot1st.springboot1.DbInterface.MemeInterface;

public interface MemeModal extends MongoRepository<MemeInterface, String> {
//    public Optional<MemeInterface> findByIdName(String idName);

    public Optional<MemeInterface> findByIdNameAndUserId(String idName,String userId);
    public List<?> findAllByUserId(String userId);

    public Optional<MemeInterface> deleteByIdNameAndUserId(String idName,String userId);
}
