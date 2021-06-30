// Generated from MathExpression.g4 by ANTLR 4.9.2

	package ads.diff.ast.generated;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MathExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		FUNC_NAME=1, PLUS=2, MINUS=3, MULTIPLY=4, DIVIDE=5, POWER=6, LPAREN=7, 
		RPAREN=8, LBRACKET=9, RBRACKET=10, FLOATING_POINT=11, INTEGER=12, DIGITS=13, 
		PI=14, E=15, IDENTIFIER=16, WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"FUNC_NAME", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "POWER", "LPAREN", 
			"RPAREN", "LBRACKET", "RBRACKET", "FLOATING_POINT", "INTEGER", "DIGITS", 
			"PI", "E", "IDENTIFIER", "WS"
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


	public MathExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MathExpression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u00d0\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2\u0099\n\2\3\3\3\3\3"+
		"\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\5"+
		"\f\u00ae\n\f\3\f\3\f\3\f\3\f\3\r\5\r\u00b5\n\r\3\r\3\r\3\16\6\16\u00ba"+
		"\n\16\r\16\16\16\u00bb\3\17\3\17\3\17\3\20\3\20\3\21\3\21\7\21\u00c5\n"+
		"\21\f\21\16\21\u00c8\13\21\3\22\6\22\u00cb\n\22\r\22\16\22\u00cc\3\22"+
		"\3\22\2\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23\3\2\26\4\2CCcc\4\2DDdd\4\2UUuu\4\2EEee\4\2"+
		"QQqq\4\2JJjj\4\2VVvv\4\2GGgg\4\2KKkk\4\2PPpp\4\2ZZzz\4\2RRrr\4\2NNnn\4"+
		"\2IIii\4\2SSss\4\2TTtt\3\2\62;\5\2C\\aac|\5\2\62;C\\c|\5\2\13\f\17\17"+
		"\"\"\2\u00f0\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\3\u0098\3\2\2\2\5\u009a\3\2\2\2\7\u009c\3\2\2\2\t\u009e"+
		"\3\2\2\2\13\u00a0\3\2\2\2\r\u00a2\3\2\2\2\17\u00a4\3\2\2\2\21\u00a6\3"+
		"\2\2\2\23\u00a8\3\2\2\2\25\u00aa\3\2\2\2\27\u00ad\3\2\2\2\31\u00b4\3\2"+
		"\2\2\33\u00b9\3\2\2\2\35\u00bd\3\2\2\2\37\u00c0\3\2\2\2!\u00c2\3\2\2\2"+
		"#\u00ca\3\2\2\2%&\t\2\2\2&\'\t\3\2\2\'\u0099\t\4\2\2()\t\2\2\2)*\t\5\2"+
		"\2*+\t\6\2\2+\u0099\t\4\2\2,-\t\2\2\2-.\t\5\2\2./\t\6\2\2/\60\t\4\2\2"+
		"\60\u0099\t\7\2\2\61\62\t\2\2\2\62\63\t\5\2\2\63\64\t\6\2\2\64\u0099\t"+
		"\b\2\2\65\66\t\2\2\2\66\67\t\5\2\2\678\t\6\2\289\t\b\2\29\u0099\t\7\2"+
		"\2:;\t\2\2\2;<\t\5\2\2<=\t\4\2\2=\u0099\t\5\2\2>?\t\2\2\2?@\t\5\2\2@A"+
		"\t\4\2\2AB\t\5\2\2B\u0099\t\7\2\2CD\t\2\2\2DE\t\4\2\2EF\t\t\2\2F\u0099"+
		"\t\5\2\2GH\t\2\2\2HI\t\4\2\2IJ\t\t\2\2JK\t\5\2\2K\u0099\t\7\2\2LM\t\2"+
		"\2\2MN\t\4\2\2NO\t\n\2\2O\u0099\t\13\2\2PQ\t\2\2\2QR\t\4\2\2RS\t\n\2\2"+
		"ST\t\13\2\2T\u0099\t\7\2\2UV\t\2\2\2VW\t\b\2\2WX\t\2\2\2X\u0099\t\13\2"+
		"\2YZ\t\2\2\2Z[\t\b\2\2[\\\t\2\2\2\\]\t\13\2\2]\u0099\t\7\2\2^_\t\5\2\2"+
		"_`\t\6\2\2`\u0099\t\4\2\2ab\t\5\2\2bc\t\6\2\2cd\t\4\2\2d\u0099\t\7\2\2"+
		"ef\t\5\2\2fg\t\6\2\2g\u0099\t\b\2\2hi\t\5\2\2ij\t\6\2\2jk\t\b\2\2k\u0099"+
		"\t\7\2\2lm\t\5\2\2mn\t\4\2\2n\u0099\t\5\2\2op\t\5\2\2pq\t\4\2\2qr\t\5"+
		"\2\2r\u0099\t\7\2\2st\t\t\2\2tu\t\f\2\2u\u0099\t\r\2\2vw\t\16\2\2w\u0099"+
		"\t\13\2\2xy\t\16\2\2yz\t\6\2\2z{\t\17\2\2{|\5\23\n\2|}\5\33\16\2}~\5\25"+
		"\13\2~\u0099\3\2\2\2\177\u0080\t\4\2\2\u0080\u0081\t\t\2\2\u0081\u0099"+
		"\t\5\2\2\u0082\u0083\t\4\2\2\u0083\u0084\t\t\2\2\u0084\u0085\t\5\2\2\u0085"+
		"\u0099\t\7\2\2\u0086\u0087\t\4\2\2\u0087\u0088\t\n\2\2\u0088\u0099\t\13"+
		"\2\2\u0089\u008a\t\4\2\2\u008a\u008b\t\n\2\2\u008b\u008c\t\13\2\2\u008c"+
		"\u0099\t\7\2\2\u008d\u008e\t\4\2\2\u008e\u008f\t\20\2\2\u008f\u0090\t"+
		"\21\2\2\u0090\u0099\t\b\2\2\u0091\u0092\t\b\2\2\u0092\u0093\t\2\2\2\u0093"+
		"\u0099\t\13\2\2\u0094\u0095\t\b\2\2\u0095\u0096\t\2\2\2\u0096\u0097\t"+
		"\13\2\2\u0097\u0099\t\7\2\2\u0098%\3\2\2\2\u0098(\3\2\2\2\u0098,\3\2\2"+
		"\2\u0098\61\3\2\2\2\u0098\65\3\2\2\2\u0098:\3\2\2\2\u0098>\3\2\2\2\u0098"+
		"C\3\2\2\2\u0098G\3\2\2\2\u0098L\3\2\2\2\u0098P\3\2\2\2\u0098U\3\2\2\2"+
		"\u0098Y\3\2\2\2\u0098^\3\2\2\2\u0098a\3\2\2\2\u0098e\3\2\2\2\u0098h\3"+
		"\2\2\2\u0098l\3\2\2\2\u0098o\3\2\2\2\u0098s\3\2\2\2\u0098v\3\2\2\2\u0098"+
		"x\3\2\2\2\u0098\177\3\2\2\2\u0098\u0082\3\2\2\2\u0098\u0086\3\2\2\2\u0098"+
		"\u0089\3\2\2\2\u0098\u008d\3\2\2\2\u0098\u0091\3\2\2\2\u0098\u0094\3\2"+
		"\2\2\u0099\4\3\2\2\2\u009a\u009b\7-\2\2\u009b\6\3\2\2\2\u009c\u009d\7"+
		"/\2\2\u009d\b\3\2\2\2\u009e\u009f\7,\2\2\u009f\n\3\2\2\2\u00a0\u00a1\7"+
		"\61\2\2\u00a1\f\3\2\2\2\u00a2\u00a3\7`\2\2\u00a3\16\3\2\2\2\u00a4\u00a5"+
		"\7*\2\2\u00a5\20\3\2\2\2\u00a6\u00a7\7+\2\2\u00a7\22\3\2\2\2\u00a8\u00a9"+
		"\7]\2\2\u00a9\24\3\2\2\2\u00aa\u00ab\7_\2\2\u00ab\26\3\2\2\2\u00ac\u00ae"+
		"\7/\2\2\u00ad\u00ac\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af\3\2\2\2\u00af"+
		"\u00b0\5\33\16\2\u00b0\u00b1\7\60\2\2\u00b1\u00b2\5\33\16\2\u00b2\30\3"+
		"\2\2\2\u00b3\u00b5\7/\2\2\u00b4\u00b3\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5"+
		"\u00b6\3\2\2\2\u00b6\u00b7\5\33\16\2\u00b7\32\3\2\2\2\u00b8\u00ba\t\22"+
		"\2\2\u00b9\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb"+
		"\u00bc\3\2\2\2\u00bc\34\3\2\2\2\u00bd\u00be\t\r\2\2\u00be\u00bf\t\n\2"+
		"\2\u00bf\36\3\2\2\2\u00c0\u00c1\t\t\2\2\u00c1 \3\2\2\2\u00c2\u00c6\t\23"+
		"\2\2\u00c3\u00c5\t\24\2\2\u00c4\u00c3\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6"+
		"\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\"\3\2\2\2\u00c8\u00c6\3\2\2\2"+
		"\u00c9\u00cb\t\25\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ca"+
		"\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00cf\b\22\2\2"+
		"\u00cf$\3\2\2\2\t\2\u0098\u00ad\u00b4\u00bb\u00c6\u00cc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}