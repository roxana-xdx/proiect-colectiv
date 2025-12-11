import { Link } from "react-router-dom";
import Navigation from "./Navigation.tsx";

interface NewsItem {
  id: number;
  title: string;
  preview: string;
}

const newsItems: NewsItem[] = [
  {
    id: 1,
    title: "PROGRAM 1 DECEMBRIE 2025",
    preview:
      "In data de 1 Decembrie 2025, cu ocazia Zilei Nationale a Romaniei, ...",
  },
  {
    id: 2,
    title: "Excursie Muzeu de Arta - 12 Decembrie 2025",
    preview:
      "In data de 12 Decembrie 2025, elevii doritori sa participe la excursia . . .",
  },
  {
    id: 3,
    title: "News no. 3",
    preview:
      " ",
  },
];

export default function News() {
  return (
    <div className="min-h-screen bg-[#F9F8F6]">
      <Navigation />

      <div className="max-w-[1440px] mx-auto px-4 md:px-8 lg:px-16 py-8">
        <div className="bg-[#EFE9E3] rounded-[30px] p-6 md:p-12 lg:p-16">
          <div className="space-y-8 md:space-y-12">
            {newsItems.map((item, index) => (
              <article
                key={item.id}
                className={`${
                  index !== newsItems.length - 1
                    ? "pb-8 md:pb-12 border-b border-[#665B4E]/20"
                    : ""
                }`}
              >
                <h2
                  className="text-[#665B4E] font-bold tracking-[0.03em] leading-[117.504%] mb-4"
                  style={{ fontSize: 'clamp(28px, 5vw, 40px)' }}
                >
                  <Link to={`/news/${item.id}`}> {item.title} </Link>
                                  </h2>
                <div className="flex flex-col md:flex-row md:items-end md:justify-between gap-4">
                  <p
                    className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%] flex-1"
                    style={{ fontSize: 'clamp(16px, 3vw, 24px)' }}
                  >
                    {item.preview}
                  </p>
                  <Link
                    to={`/news/${item.id}`}
                    className="text-[#665B4E]/80 font-bold tracking-[0.03em] leading-[117.504%] hover:text-[#665B4E] transition-colors whitespace-nowrap self-end"
                    style={{ fontSize: 'clamp(16px, 3vw, 24px)' }}
                  >
                    See more→
                  </Link>
                </div>
              </article>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
