import { Link, useParams, useNavigate } from "react-router-dom";


const newsContent: Record<string, { title: string; content: string }> = {
  "1": {
    title: "PROGRAM 1 DECEMBRIE 2025",
    content: `In data de 1 Decembrie 2025, cu ocazia Zilei Nationale a Romaniei, nu se vor desfasura meditatii sau activitati suplimentare, intrucat ziua este libera pentru toti elevii si profesorii. 
    
    Orele si sedintele de pregatire se vor relua conform programului obisnuit in ziua urmatoare. Elevii sunt rugati sa profite de aceasta pauza pentru odihna si pentru a petrece timp alaturi de familie. 
    
    In cazul intrebarilor privind reprogramarea meditatiilor, va puteti adresa profesorilor coordonatori incepand cu data de 2 Decembrie. Va dorim o zi linistita.`,
  },
  "2": {
    title: "Excursie Muzeu de Arta - 12 Decembrie 2025",
    content: `     In data de 12 Decembrie 2025, elevii doritori sa participe la excursia organizata de scoala sunt rugati sa se inscrie din timp la secretariat. 
    
    Excursia va include vizitarea unor obiective culturale si istorice, precum si activitati recreationale.
    
    Costurile, programul detaliat si regulile de participare vor fi afisate pe panoul informational al scolii. Locurile sunt limitate, de aceea elevii interesati sunt rugati sa confirme participarea cat mai curand. 
    
    Va invitam sa profitati de aceasta oportunitate de invatare si socializare.`,
  },
  "3": {
    title: "News no. 3",
    content: `   `,
  },
};

export default function NewsDetail() {
  const { id } = useParams();
  const news = newsContent[id || "1"];

  const navigate = useNavigate();

  if (!news) {
    return (
      <div className="min-h-screen bg-[#F9F8F6] flex items-center justify-center">
        <p className="text-2xl text-[#665B4E]">News not found (boring init?)</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <div className="w-full px-4 md:px-8 lg:px-16 py-6">
        <div className="max-w-[1440px] mx-auto flex flex-col md:flex-row items-center justify-between gap-4">
          <Link
            to="/news"
            className="flex items-center justify-center bg-[#EFE9E3] rounded-[20px] px-8 py-6 min-h-[79px]"
          >
            <h2
              className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%]"
              style={{ fontSize: "clamp(24px, 4vw, 40px)" }}
            >
              After School
            </h2>
          </Link>

          <nav className="flex flex-wrap items-center justify-center gap-3 md:gap-4">
            <Link
              to="/news"
              className="px-6 md:px-8 py-5 rounded-[20px] min-w-[140px] md:min-w-[170px] h-[70px] flex items-center justify-center text-2xl font-bold tracking-[0.03em] leading-[117.504%] bg-[#EFE9E3] text-[#665B4E] hover:bg-[#E5DED6] transition-colors"
            >
              Back
            </Link>

            <button className="px-6 md:px-8 py-5 rounded-[20px] min-w-[140px] md:min-w-[170px] h-[70px] flex items-center justify-center text-2xl font-bold tracking-[0.03em] leading-[117.504%] bg-[#EFE9E3] text-[#665B4E] hover:bg-[#E5DED6] transition-colors">
              Edit News
            </button>
            <div>
            <Link 
            to="/addnews" 
             className="px-6 md:px-8 py-5 rounded-[20px] min-w-[140px] md:min-w-[170px] h-[70px] flex items-center justify-center text-2xl font-bold tracking-[0.03em] leading-[117.504%] bg-[#9CB0C9] text-[#F9F8F6] hover:bg-[#8BA0B8] transition-colors"
              >
             Add New
            </Link>
            </div>
          </nav>
        </div>
      </div>

      <div className="max-w-[1440px] mx-auto px-4 md:px-8 lg:px-16 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12 lg:p-16">
          <div className="max-w-[1307px] mx-auto">
            <h1
              className="text-[#665B4E] text-center font-bold tracking-[0.03em] leading-[117.504%] mb-8 md:mb-12"
              style={{ fontSize: "clamp(28px, 5vw, 40px)" }}
            >
              {news.title}
            </h1>

            <div className="px-4 md:px-16">
              <p
                className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%] whitespace-pre-line"
                style={{ fontSize: "clamp(18px, 3vw, 32px)" }}
              >
                {news.content}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
