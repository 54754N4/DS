// Generated from MathExpression.g4 by ANTLR 4.9.2

	package ads.diff.ast.generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MathExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		FUNC_NAME=1, PLUS=2, MINUS=3, MULTIPLY=4, DIVIDE=5, POWER=6, LPAREN=7, 
		RPAREN=8, LBRACKET=9, RBRACKET=10, FLOATING_POINT=11, INTEGER=12, DIGITS=13, 
		PI=14, E=15, IDENTIFIER=16, WS=17;
	public static final int
		RULE_input = 0, RULE_expression = 1, RULE_term = 2, RULE_power = 3, RULE_atom = 4, 
		RULE_nested = 5, RULE_constants = 6, RULE_funcExpression = 7, RULE_number = 8, 
		RULE_unary = 9, RULE_additiveOps = 10, RULE_multiplicativeOps = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"input", "expression", "term", "power", "atom", "nested", "constants", 
			"funcExpression", "number", "unary", "additiveOps", "multiplicativeOps"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'+'", "'-'", "'*'", "'/'", "'^'", "'('", "')'", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "FUNC_NAME", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "POWER", "LPAREN", 
			"RPAREN", "LBRACKET", "RBRACKET", "FLOATING_POINT", "INTEGER", "DIGITS", 
			"PI", "E", "IDENTIFIER", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MathExpression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MathExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class InputContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MathExpressionParser.EOF, 0); }
		public InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitInput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InputContext input() throws RecognitionException {
		InputContext _localctx = new InputContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			expression();
			setState(25);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public UnaryContext unary() {
			return getRuleContext(UnaryContext.class,0);
		}
		public List<AdditiveOpsContext> additiveOps() {
			return getRuleContexts(AdditiveOpsContext.class);
		}
		public AdditiveOpsContext additiveOps(int i) {
			return getRuleContext(AdditiveOpsContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(27);
				unary();
				}
			}

			setState(30);
			term();
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(31);
				additiveOps();
				setState(32);
				term();
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public List<PowerContext> power() {
			return getRuleContexts(PowerContext.class);
		}
		public PowerContext power(int i) {
			return getRuleContext(PowerContext.class,i);
		}
		public List<MultiplicativeOpsContext> multiplicativeOps() {
			return getRuleContexts(MultiplicativeOpsContext.class);
		}
		public MultiplicativeOpsContext multiplicativeOps(int i) {
			return getRuleContext(MultiplicativeOpsContext.class,i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			power();
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTIPLY || _la==DIVIDE) {
				{
				{
				setState(40);
				multiplicativeOps();
				setState(41);
				power();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PowerContext extends ParserRuleContext {
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public List<TerminalNode> POWER() { return getTokens(MathExpressionParser.POWER); }
		public TerminalNode POWER(int i) {
			return getToken(MathExpressionParser.POWER, i);
		}
		public PowerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_power; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterPower(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitPower(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitPower(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PowerContext power() throws RecognitionException {
		PowerContext _localctx = new PowerContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_power);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			atom();
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==POWER) {
				{
				{
				setState(49);
				match(POWER);
				setState(50);
				atom();
				}
				}
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public FuncExpressionContext funcExpression() {
			return getRuleContext(FuncExpressionContext.class,0);
		}
		public NestedContext nested() {
			return getRuleContext(NestedContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ConstantsContext constants() {
			return getRuleContext(ConstantsContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atom);
		try {
			setState(60);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUNC_NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				funcExpression();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				nested();
				}
				break;
			case FLOATING_POINT:
			case INTEGER:
				enterOuterAlt(_localctx, 3);
				{
				setState(58);
				number();
				}
				break;
			case PI:
			case E:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(59);
				constants();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NestedContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(MathExpressionParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MathExpressionParser.RPAREN, 0); }
		public NestedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nested; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterNested(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitNested(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitNested(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NestedContext nested() throws RecognitionException {
		NestedContext _localctx = new NestedContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_nested);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(LPAREN);
			setState(63);
			expression();
			setState(64);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantsContext extends ParserRuleContext {
		public TerminalNode PI() { return getToken(MathExpressionParser.PI, 0); }
		public TerminalNode E() { return getToken(MathExpressionParser.E, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MathExpressionParser.IDENTIFIER, 0); }
		public ConstantsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constants; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterConstants(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitConstants(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitConstants(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantsContext constants() throws RecognitionException {
		ConstantsContext _localctx = new ConstantsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_constants);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PI) | (1L << E) | (1L << IDENTIFIER))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncExpressionContext extends ParserRuleContext {
		public TerminalNode FUNC_NAME() { return getToken(MathExpressionParser.FUNC_NAME, 0); }
		public TerminalNode LPAREN() { return getToken(MathExpressionParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MathExpressionParser.RPAREN, 0); }
		public FuncExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterFuncExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitFuncExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitFuncExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncExpressionContext funcExpression() throws RecognitionException {
		FuncExpressionContext _localctx = new FuncExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_funcExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(FUNC_NAME);
			setState(69);
			match(LPAREN);
			setState(70);
			expression();
			setState(71);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode FLOATING_POINT() { return getToken(MathExpressionParser.FLOATING_POINT, 0); }
		public TerminalNode INTEGER() { return getToken(MathExpressionParser.INTEGER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			_la = _input.LA(1);
			if ( !(_la==FLOATING_POINT || _la==INTEGER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(MathExpressionParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MathExpressionParser.MINUS, 0); }
		public UnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryContext unary() throws RecognitionException {
		UnaryContext _localctx = new UnaryContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_unary);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveOpsContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(MathExpressionParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MathExpressionParser.MINUS, 0); }
		public AdditiveOpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveOps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterAdditiveOps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitAdditiveOps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitAdditiveOps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveOpsContext additiveOps() throws RecognitionException {
		AdditiveOpsContext _localctx = new AdditiveOpsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_additiveOps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeOpsContext extends ParserRuleContext {
		public TerminalNode MULTIPLY() { return getToken(MathExpressionParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(MathExpressionParser.DIVIDE, 0); }
		public MultiplicativeOpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeOps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).enterMultiplicativeOps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MathExpressionListener ) ((MathExpressionListener)listener).exitMultiplicativeOps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MathExpressionVisitor ) return ((MathExpressionVisitor<? extends T>)visitor).visitMultiplicativeOps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeOpsContext multiplicativeOps() throws RecognitionException {
		MultiplicativeOpsContext _localctx = new MultiplicativeOpsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_multiplicativeOps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_la = _input.LA(1);
			if ( !(_la==MULTIPLY || _la==DIVIDE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23T\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\3\2\3\2\3\3\5\3\37\n\3\3\3\3\3\3\3\3\3\7\3%\n\3\f\3"+
		"\16\3(\13\3\3\4\3\4\3\4\3\4\7\4.\n\4\f\4\16\4\61\13\4\3\5\3\5\3\5\7\5"+
		"\66\n\5\f\5\16\59\13\5\3\6\3\6\3\6\3\6\5\6?\n\6\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\2\2\16\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\2\6\3\2\20\22\3\2\r\16\3\2\4\5\3\2\6\7\2"+
		"N\2\32\3\2\2\2\4\36\3\2\2\2\6)\3\2\2\2\b\62\3\2\2\2\n>\3\2\2\2\f@\3\2"+
		"\2\2\16D\3\2\2\2\20F\3\2\2\2\22K\3\2\2\2\24M\3\2\2\2\26O\3\2\2\2\30Q\3"+
		"\2\2\2\32\33\5\4\3\2\33\34\7\2\2\3\34\3\3\2\2\2\35\37\5\24\13\2\36\35"+
		"\3\2\2\2\36\37\3\2\2\2\37 \3\2\2\2 &\5\6\4\2!\"\5\26\f\2\"#\5\6\4\2#%"+
		"\3\2\2\2$!\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'\5\3\2\2\2(&\3\2\2"+
		"\2)/\5\b\5\2*+\5\30\r\2+,\5\b\5\2,.\3\2\2\2-*\3\2\2\2.\61\3\2\2\2/-\3"+
		"\2\2\2/\60\3\2\2\2\60\7\3\2\2\2\61/\3\2\2\2\62\67\5\n\6\2\63\64\7\b\2"+
		"\2\64\66\5\n\6\2\65\63\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28"+
		"\t\3\2\2\29\67\3\2\2\2:?\5\20\t\2;?\5\f\7\2<?\5\22\n\2=?\5\16\b\2>:\3"+
		"\2\2\2>;\3\2\2\2><\3\2\2\2>=\3\2\2\2?\13\3\2\2\2@A\7\t\2\2AB\5\4\3\2B"+
		"C\7\n\2\2C\r\3\2\2\2DE\t\2\2\2E\17\3\2\2\2FG\7\3\2\2GH\7\t\2\2HI\5\4\3"+
		"\2IJ\7\n\2\2J\21\3\2\2\2KL\t\3\2\2L\23\3\2\2\2MN\t\4\2\2N\25\3\2\2\2O"+
		"P\t\4\2\2P\27\3\2\2\2QR\t\5\2\2R\31\3\2\2\2\7\36&/\67>";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}