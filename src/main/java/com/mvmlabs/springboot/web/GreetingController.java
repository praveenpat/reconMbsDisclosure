package com.mvmlabs.springboot.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.compare.doc.CompareDocs;
import com.compare.doc.ComparedResults;

/**
 * Controller that demonstrates tiles mapping, reguest parameters and path variables.
 * 
 * @author Mark Meany
 */
@Controller
public class GreetingController {
	private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(value = "/home", method=RequestMethod.GET)
	public ModelAndView home() {
	    return new ModelAndView("site.homepage","cusipRequest",new CusipRequest());
	}
    
    
    @RequestMapping(value = "/uploadAndCompare", method=RequestMethod.GET)
	public ModelAndView uploadAndCompare() {
	    return new ModelAndView("site.uploadAndCompare","cusipRequest",new CusipRequest());
	}
    
    
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFileHandler(@ModelAttribute("cusipRequest")CusipRequest request) throws IOException {

		if (!request.getFileData().isEmpty()) {
			
			CompareDocs comp=new CompareDocs();
	    	
	    	try {
				List<ComparedResults> compareResults = comp.getCompareResults(request.getCusipId(),request.getFileData());
				
				request.setResults(compareResults);
				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				request.setErrorMessage("Something Went Wrong !!");
			}
			
			
		}
		
		request.setFileData(null);
		return new ModelAndView("site.uploadAndCompare","cusipRequest",request);
    }
    
    @RequestMapping(value = "/reconcileCuspid", method=RequestMethod.POST)
   	public ModelAndView home(@ModelAttribute("cusipRequest")CusipRequest request) {
    	
    	CompareDocs comp=new CompareDocs();
    	
    	try {
			List<ComparedResults> compareResults = comp.getCompareResults(request.getCusipId(),null);
			
			request.setResults(compareResults);
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			request.setErrorMessage("Something Went Wrong !!");
		}
    	
    	
    	
   	    return new ModelAndView("site.homepage","cusipRequest",request);
   	}
	
	@RequestMapping(value = "/greet", method=RequestMethod.GET)
	public ModelAndView greet(@RequestParam(value = "name", required=false, defaultValue="World!")final String name, final Model model) {
		log.info("Controller has been invoked with Request Parameter name = '" + name + "'.");
		return new ModelAndView("site.greeting", "name", name);
	}

	@RequestMapping(value = "/greet/{name}", method=RequestMethod.GET)
	public ModelAndView greetTwoWays(@PathVariable(value="name") final String name, final Model model) {
		log.info("Controller has been invoked with Path Variable name = '" + name + "'.");
		return new ModelAndView("site.greeting", "name", name);
	}
}
