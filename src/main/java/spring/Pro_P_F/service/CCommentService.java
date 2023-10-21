package spring.Pro_P_F.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Pro_P_F.domain.CComment;
import spring.Pro_P_F.repository.CCommentRepository;

@Service
public class CCommentService {
    @Autowired
    private CCommentRepository commentRepository;

    public void createComment(CComment comment) {
        commentRepository.save(comment);
    }
}
