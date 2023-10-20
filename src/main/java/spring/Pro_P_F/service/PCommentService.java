package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.PComment;
import spring.Pro_P_F.repository.PCommentRepository;

@Service
public class PCommentService {

    @Autowired
    private PCommentRepository commentRepository;

    public void createComment(PComment comment) {
        commentRepository.save(comment);
    }
}
