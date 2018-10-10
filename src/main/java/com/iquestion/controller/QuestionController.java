package com.iquestion.controller;

import com.iquestion.common.Constant;
import com.iquestion.common.Result;
import com.iquestion.pojo.Question;
import com.iquestion.service.QuestionService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @RequestMapping(value = "/question",method = RequestMethod.POST)
    @ResponseBody
    public Result addQuestion(Model model, Question question){

        try {
            questionService.add(question);
        }catch (Exception e){
            return new Result(Constant.RESULT_CODE_SERVER_ERROR,"chucuo");
        }


        return new Result(Constant.RESULT_CODE_SUCCESS,"nice");
    }
}
