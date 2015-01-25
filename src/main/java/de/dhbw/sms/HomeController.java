package de.dhbw.sms;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.dhbw.sms.services.IAbstractSyntaxTreeResult;
import de.dhbw.sms.services.InputParser;
import de.dhbw.sms.services.MathMLFactory;
import de.dhbw.sms.services.OntologyProvider;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private MathMLFactory mathMLFactory;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	public void setMathMLFactory(MathMLFactory mathMLFactory)
	{
		this.mathMLFactory = mathMLFactory;
	}
	
	public MathMLFactory getMathMLFactory()
	{
		return this.mathMLFactory;
	}
	
	private OntologyProvider ontologyProvider;
	
	@Autowired
	public void setOntolotgyProvider(OntologyProvider provider)
	{
		ontologyProvider = provider;
	}
	
	private InputParser inputParser;
	
	@Autowired
	public void setInputParser(InputParser inputParser)
	{
		this.inputParser = inputParser; 
	}
	
	public InputParser getInputParser()
	{
		return this.inputParser;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//createOntologyResult(parserResult)

		return "home";
	}
	
	/*
	 * 
	 */
	@RequestMapping(value = "ajax", method = RequestMethod.POST)
	public @ResponseBody
	OutputExpression getMathMLExpression(@RequestBody final InputExpression input, HttpServletRequest request) 
	{
		String inputExpression = input.getInput();
		logger.info("Recieved request: " + inputExpression);
		IAbstractSyntaxTreeResult parserResult = this.getInputParser().parseExperssion(inputExpression);
		
		logger.info("Send ajax response");
		createOntologyResult(parserResult);
		OutputExpression result = createOutputExpression(parserResult);
		return result;
	}
	
	private OutputExpression createOutputExpression(IAbstractSyntaxTreeResult parserResult)
	{
		OutputExpression result = new OutputExpression();
		if(parserResult.hasParsed())
		{
			String mathml = getMathMLFactory().getMathML(parserResult.getAbstractSyntaxTree());
			result.setOutput(mathml);
		}
		else
			result.setOutput("<math xmlns=\"http://www.w3.org/1998/Math/MathML\"></math>");
		return result;
	}
	
	private void createOntologyResult(IAbstractSyntaxTreeResult parserResult)
	{
		ontologyProvider.initialize(servletContext.getRealPath("/resources/Ontologies/sms2.owl"));
		if(parserResult.hasParsed())
			ontologyProvider.searchFor(parserResult.getAbstractSyntaxTree());
	}
	
}
