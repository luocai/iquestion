package com.iquestion.controller;


import com.iquestion.common.ViewObject;
import com.iquestion.pojo.Question;
import com.iquestion.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    QuestionService questionService;

    @RequestMapping(path={"/","/index"})
    public String index(Model model){

        List<Question> questions = questionService.queryAll();

        List<ViewObject> vos = new ArrayList<>();

        for(Question question:questions){
            ViewObject vo = new ViewObject();
            vo.setData("question",question);
            vo.setData("userId",question.getUserId());

            vos.add(vo);
        }
System.out.println("ceshiceshi");
        model.addAttribute("vos",vos);

        return "index";
    }
}
