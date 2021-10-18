package org.example.service;

import org.example.entity.TpJob;
import org.example.dto.TpJobDTO;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author AI
 * @since 2021-10-18
 */
public interface ITpJobService  {

        IPage<TpJob> page(TpJobDTO dto) ;

        List<TpJob> list(TpJobDTO dto) ;

        Integer save(TpJobDTO dto) ;

        Integer updateById(TpJobDTO dto) ;

        Integer deleteLogic(Integer id) ;

        TpJob getOne(TpJobDTO dto) ;

        public Integer buildJob(String jobName);

        public Integer disableJob(String jobName);

        public Integer enableJob(String jobName);
}
