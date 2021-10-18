package org.example.service;

import org.example.entity.TpBuild;
import org.example.dto.TpBuildDTO;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author AI
 * @since 2021-10-18
 */
public interface ITpBuildService  {

        IPage<TpBuild> page(TpBuildDTO dto) ;

        List<TpBuild> list(TpBuildDTO dto) ;

        Integer save(TpBuildDTO dto) ;

        Integer updateById(TpBuildDTO dto) ;

        Integer deleteLogic(List<Integer> toIntList) ;

        TpBuild getOne(TpBuildDTO dto) ;
}
